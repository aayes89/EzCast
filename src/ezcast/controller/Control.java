package ezcast.controller;

import ezcast.model.Devices;
import ezcast.model.servicios.DialService;
import ezcast.utils.Connector;
import ezcast.utils.Constantes;
import ezcast.utils.Utiles;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import ezcast.utils.LoggerHelper;
import java.util.logging.Level;


/**
 *
 * @author AyA
 */
public class Control implements Serializable {

    private int delay;
    private Utiles util;
    private Constantes cons;
    private MulticastSocket ms;
    private Socket sock;
    private InetAddress iaddrs;
    private LinkedList<Devices> deviceList = new LinkedList<>();
    private LinkedList<String> devListed = new LinkedList<>();
    private String uriLoad;
    private Connector con;
    private NetworkInterface ni;
    private LoggerHelper log;

    public Control() {
        delay = 3000; // 10 seconds
        log=new LoggerHelper();
        cons = new Constantes();
        con = new Connector();        
        /*new Retrofit.Builder()
         .baseUrl("http://192.168.0.112:50000")
         .addConverterFactory(
         GsonConverterFactory.create(new GsonBuilder()
         .serializeNulls()
         .create()
         )
         )
         .build();
         */
        try {
            util = new Utiles();
            ms = new MulticastSocket(1900);
            iaddrs = InetAddress.getByName("239.255.255.250");
        } catch (IOException ex) {
            System.out.println("Constructor IOE: " + ex.getMessage());            
            log.addLog(Level.SEVERE, "IOE at constructor: "+ ex.getMessage());
        }
        loadStaticVideoLink();
    }

    public void lookupDevices() {
        try {
            ni = util.getMyNetworkInterface();
            ms.setReuseAddress(true);
            ms.joinGroup(new InetSocketAddress(iaddrs, 1900), ni);
            ms.send(new DatagramPacket(Constantes.getSendMsg().getBytes(StandardCharsets.UTF_8), Constantes.getSendMsg().length(), iaddrs, 1900));
            int oldTime = (int) System.currentTimeMillis();
            int counter = 1;
            while ((int) System.currentTimeMillis() < (oldTime + delay)) {
                System.out.println(counter + "sec");
                counter++;
                findDevices();
            }
            if (!devListed.isEmpty()) {
                System.out.println("Devices found: " + devListed.size());
                //System.out.println(devListed.getFirst());
            } else {
                System.out.println("No devices found");
            }
            //}
            ms.leaveGroup(new InetSocketAddress(iaddrs, 1900), ni);

        } catch (SocketException ex) {
            log.addLog(Level.SEVERE, "Socket Exception at lookout device method: "+ ex.getMessage());            
            System.out.println("SocketException: " + ex.getMessage());
        } catch (IOException ex) {
            log.addLog(Level.SEVERE, "IOE at lookout device method: "+ ex.getMessage());            
            System.out.println("IOE: " + ex.getMessage());
        }
        loadUnkownDevice();
    }

    private void loadUnkownDevice() {
        Devices unknownDev = new Devices();
        unknownDev.setHost("http://192.168.0.159:38389/deviceDescription/MediaServer");
        unknownDev.setPort(38389);
        unknownDev.setDevType("urn:dial-multiscreen-org:device:dial:1");
        unknownDev.setName("Atzin");
        unknownDev.setManufacturer("Amazon");
        unknownDev.setModelName("AEOCN");
        unknownDev.setUdn("uuid:9255d318-a9dd-3375-bc37-ccb52fdb8ede");
        unknownDev.addService(
                new DialService(
                        "/upnp/dev/9255d318-a9dd-3375-bc37-ccb52fdb8ede/svc/dial-multiscreen-org/dial/desc",
                        "/upnp/dev/9255d318-a9dd-3375-bc37-ccb52fdb8ede/svc/dial-multiscreen-org/dial/action",
                        "/upnp/dev/9255d318-a9dd-3375-bc37-ccb52fdb8ede/svc/dial-multiscreen-org/dial/event",
                        "urn:dial-multiscreen-org:service:dial:1",
                        "urn:dial-multiscreen-org:serviceId:dial",
                "dial"));
        deviceList.add(unknownDev);
    }

    private void findDevices() throws IOException {

        byte[] buff = new byte[Byte.MAX_VALUE];
        ms.receive(new DatagramPacket(buff, buff.length, iaddrs, 1900));
        String data = (util.ByteConverter(buff));
        if (data.contains("Location")) {
            //System.out.println("Data:\n" + data);
            String urlDev = "";
            for (int i = data.indexOf("Location:") + 10; i < data.length(); i++) {
                char let = data.charAt(i);
                if (let == '\n') {
                    break;
                } else {
                    urlDev += let;
                }
            }
            if (urlDev.length() > 20 && !existUrlOnList(urlDev)) {
                devListed.add(urlDev);
            }
        }
    }

    private boolean existUrlOnList(String url) {
        boolean result = false;
        for (String str : devListed) {
            if (str.contains(url)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean existDevOnList(String name) {
        boolean result = false;
        for (Devices dev : deviceList) {
            if (dev.getName().equalsIgnoreCase(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void loadMainConfig() {
        for (String devUrl : devListed) {
            if (devUrl != null) {
                String responseBody = con.getRequest(devUrl);
                Devices dev = util.getDeviceFromXML(responseBody);
                if (dev != null) {
                    dev.setHost(getHost(devUrl));
                    dev.setPort(getPort(devUrl));
                    if (!existDevOnList(dev.getName())) {
                        addDevice(dev);
                    }
                }
            }
        }
    }

    public void SendDataToDevice(String dataToSend, String SoapAction, Devices dev) {
        if (con.sendData(dataToSend, SoapAction, dev)) {
            System.out.println("Datos enviados");
        } else {
            System.out.println("Error al enviar los datos");
            log.addLog(Level.SEVERE, "Error al enviar los datos");
        }
    }
    
    public Connector getConnector(){
        return con;
    }
    public Utiles getUtils(){
        return util;
    }

    public boolean openConnectionWell(String host) {
        boolean result = false;
        try {
            URL link = new URL(host);
            //"192.168.0.112"
            sock = new Socket(host, 50000);
            result = sock.isConnected();
            /*urlCon = (HttpURLConnection) link.openConnection();
             urlCon.setRequestProperty("Method:", "GET");            
             urlCon.setReadTimeout(5000);
             result = (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK);            
             urlCon.disconnect();
             */
        } catch (MalformedURLException ex) {
            log.addLog(Level.SEVERE, "Malformed URL at openConWell method: "+ ex.getMessage());                        
            System.out.println("MalformedURLException: " + ex.getMessage());
        } catch (IOException ex) {
            log.addLog(Level.SEVERE, "IOE at openConWell method: "+ ex.getMessage());                        
            System.out.println("IOE: " + ex.getMessage());
        }
        return result;
    }

    public void addDevice(Devices dev) {
        deviceList.add(dev);
    }

    public Devices getDevice(String ip) {
        Devices tmpDev = null;
        for (Devices dev : deviceList) {
            if (dev.getHost().contains(ip)) {
                tmpDev = dev;
                break;
            }
        }
        return tmpDev;
    }

    public LinkedList<Devices> getDevices() {
        return deviceList;
    }

    public void loadVideoLink(String url) {
        //"http://192.168.0.163:8081/"
        this.uriLoad = "<u:SetAVTransportURI xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID>0</InstanceID><CurrentURI>" + url + "</CurrentURI><CurrentURIMetaData>URL: " + url + "</CurrentURIMetaData></u:SetAVTransportURI>\n";
    }

    public void loadStaticVideoLink() {        
        this.uriLoad = "<u:SetAVTransportURI xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID>0</InstanceID><CurrentURI>http://"+util.getMyIP()+":8081/video.mp4</CurrentURI><CurrentURIMetaData>Multimedia Web Streaming</CurrentURIMetaData></u:SetAVTransportURI>\n";        
    }

    public String getUriLoad() {
        return uriLoad;
    }

    public void addUrlToDevLost(String link) {
        devListed.add(link);
    }

    public void LoadDeviceList(LinkedList<Devices> dlist) {
        deviceList.addAll(dlist);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getHost(String link) {
        String[] splt = link.split(":");
        return splt[0] + ":" + splt[1];
    }

    public int getPort(String link) {
        int port = 80;
        if (link.contains("http")) {
            link = link.substring(link.indexOf("http") + 6);
            //System.out.println(link);
            int index = link.indexOf(":") + 1;
            if (index > 0) {
                String[] cap = link.split(":")[1].split("/");
                port = Integer.valueOf(cap[0]);
            }
        }
        return port;
    }
    
    public LoggerHelper getLogs(){
        return log;
    }
}
