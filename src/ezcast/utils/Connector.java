package ezcast.utils;

/**
 *
 * @author AyA
 */
import ezcast.model.Devices;
import ezcast.model.Servicios;
import ezcast.model.servicios.AVTransportService;
import ezcast.model.servicios.ConnectionManagerService;
import ezcast.model.servicios.RenderingControlService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

public class Connector implements Serializable {

    private String url;
    HttpsURLConnection myConnection;
    HttpURLConnection urlCon;
    URL myUrl;
    private LoggerHelper logs;

    public Connector() {
        logs = new LoggerHelper();
    }

    public LoggerHelper getLogs() {
        return logs;
    }

    public String getRequest(String request) {
        this.url = request;
        System.out.println("URL acquired: " + this.url);
        int type = 0;
        if (url.startsWith("http://")) {
            type = 0;
        } else if (url.startsWith("https://")) {
            type = 1;
        }
        Connect(type);
        return getDataReaded(type);
    }

    private void Connect(int type) {
        try {
            myUrl = new URL(this.url);
            if (type == 1) {
                myConnection = (HttpsURLConnection) myUrl.openConnection();
                myConnection.setRequestProperty("User-Agent", "RestApp-v0.1");
                myConnection.setRequestProperty("Accept",
                        "application/vnd.github.v3+json");
                //myConnection.setRequestProperty("Contact-Me","hathibelagal@example.com");
            } else if (type == 0) {
                urlCon = (HttpURLConnection) myUrl.openConnection();
                urlCon.setRequestProperty("User-Agent", "RestApp-v0.1");
                urlCon.setRequestProperty("Accept",
                        "application/vnd.github.v3+json");
            }
        } catch (MalformedURLException ex) {
            System.out.println("MalformedUrlExc: " + ex.getMessage());
            logs.addLog(Level.SEVERE, "Malformed URL at connect method: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOE: " + ex.getMessage());
            logs.addLog(Level.SEVERE, "IOE at connect method: " + ex.getMessage());            
        }
    }

    public boolean sendData(String dataSend, String SoapAction, Devices dev) {
        boolean result = false;
        try {
            //sock.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));            
            String hostUrl = dev.getHost() + ":" + dev.getPort();
            String controlURL = "";
            if (SoapAction.contains("AVTransport")) {
                for (Servicios s : dev.getServiceList()) {
                    if (s instanceof AVTransportService) {
                        controlURL = s.getControlURL();
                        break;
                    }
                }
            } else if (SoapAction.contains("RenderingControl")) {
                for (Servicios s : dev.getServiceList()) {
                    if (s instanceof RenderingControlService) {
                        controlURL = s.getControlURL();
                    }
                }
            } else if (SoapAction.contains("ConnectionManager")) {
                for (Servicios s : dev.getServiceList()) {
                    if (s instanceof ConnectionManagerService) {
                        controlURL = s.getControlURL();
                        break;
                    }
                }
            }
            myUrl = new URL(hostUrl + controlURL);
            System.out.println("Full path: " + myUrl.toString());
            urlCon = (HttpURLConnection) myUrl.openConnection();
            //String data ="POST "+dev.getServiceList().getFirst().getControlURL()+" HTTP/1.1\n"+dataSend;
            //urlCon.setRequestProperty("Method:", "POST");

            urlCon.setRequestProperty("HOST", dev.getHost() + ":" + dev.getPort());
            urlCon.setRequestProperty("Content-Length", dataSend.length() + "");
            urlCon.setRequestProperty("Connection", "Close");
            urlCon.setRequestProperty("Accept-Ranges", "bytes");
            urlCon.setRequestProperty("Content-type", "text/xml; charset=utf-8;");
            urlCon.setRequestProperty("Pragma", "no-cache");
            urlCon.setRequestProperty("USER-AGENT", "Java/6.3 UPnP/1.0 JVM-DLNA DLNADOC/1.0");
            //"urn:schemas-upnp-org:service:AVTransport:1#SetAVTransportURI"
            //"urn:schemas-upnp-org:service:ConnectionManager:1#GetProtocolInfo"\r\n
            urlCon.setRequestProperty("SOAPAction", "urn:schemas-upnp-org:service:" + SoapAction);
            urlCon.setDoOutput(true);
            urlCon.getOutputStream().write(dataSend.getBytes(StandardCharsets.UTF_8));
            result = (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException ex) {
            System.out.println("SendData IOE: " + ex.getLocalizedMessage());
            logs.addLog(Level.SEVERE, "IOE at sendData method: " + ex.getMessage());            
            urlCon = null;
        }
        return result;
    }

    private InputStream getInputStreamOfRequest(int type) {
        InputStream is = null;
        try {
            if (type == 0) {
                is = urlCon.getInputStream();
            } else {
                is = myConnection.getInputStream();
            }
        } catch (IOException ex) {
            logs.addLog(Level.SEVERE, "IOE at getInputStreamRequest method: " + ex.getMessage());            
            System.out.println("IOE: " + ex.getMessage());
        }
        return is;
    }

    private StringBuilder readData(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String cad = "";
            while ((cad = br.readLine()) != null) {
                sb.append(cad);
            }
            br.close();
        } catch (IOException ex) {
            logs.addLog(Level.SEVERE, "IOE at readData method: " + ex.getMessage());            
            System.out.println("ReadData IOE: " + ex.getMessage());
        }
        return sb;
    }

    public String getDataReaded(int type) {
        System.out.println("type: " + type);
        InputStream ins = null;
        StringBuilder sb = new StringBuilder();
        try {
            if (urlCon != null) {
                if (urlCon.getResponseCode() == 200) {
                    ins = getInputStreamOfRequest(type);
                    sb = readData(ins);
                }
            } else if (myConnection != null) {
                if (myConnection.getResponseCode() == 200) {
                    ins = getInputStreamOfRequest(type);
                    sb = readData(ins);
                }
            }
        } catch (IOException ex) {
            logs.addLog(Level.SEVERE, "IOE at getDataReaded method: " + ex.getMessage());            
            System.out.println("getDataReaded IOE: " + ex.getMessage());
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException ex) {
                logs.addLog(Level.SEVERE, "IOE at close on getDataReaded method: " + ex.getMessage());                
                System.out.println("getDataReaded InputStream Close Op IOE: " + ex.getMessage());
            }
        }
        return sb.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
