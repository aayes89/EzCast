/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezcast.utils;

import com.google.gson.Gson;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import ezcast.model.Devices;
import ezcast.model.Servicios;
import ezcast.model.servicios.AVTransportService;
import ezcast.model.servicios.ConnectionManagerService;
import ezcast.model.servicios.ContentDirectoryService;
import ezcast.model.servicios.DialService;
import ezcast.model.servicios.ECPService;
import ezcast.model.servicios.RemoteControlService;
import ezcast.model.servicios.RenderingControlService;
import ezcast.model.servicios.X_MS_MediaReceiverRegistrar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author AyA
 */
public class Utiles implements Serializable {

    private NetworkInterface ni;
    private InetAddress ia;
    private Gson gjson;
    private LinkedList<Servicios> srvList;
    private LoggerHelper logs;

    public Utiles() {
        ni = null;
        ia = null;
        gjson = new Gson();
        logs = new LoggerHelper();
        //new GsonBuilder().setPrettyPrinting().setLenient().create();
    }

    public Devices parseJSONWithGSON(String cad) {
        return gjson.fromJson(cad, Devices.class);
        //JSONObject jobj = (JSONObject) new JSONParser().parse(cad);        
    }

    private File genTempFile(String data) {
        FileWriter fr = null;
        File fxml = null;
        try {
            fxml = new File("cap.xml");
            fr = new FileWriter(fxml);
            fr.write(data);
            fr.close();

        } catch (IOException ex) {
            logs.addLog(Level.SEVERE, "IOE on genTempFile at file create operation: " + ex.getMessage());
            System.out.println(ex.getMessage());
        } finally {
            try {
                fr.close();
                return fxml;
            } catch (IOException ex) {
                logs.addLog(Level.SEVERE, "IOE on genTempFile at close operation: " + ex.getMessage());
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    private Devices parseDeviceFromXML(org.w3c.dom.Document doc, String nameTag) {
        Devices dev = new Devices();
        org.w3c.dom.Document xmldoc = doc;
        org.w3c.dom.Element root = xmldoc.getDocumentElement();
        NodeList serviceList = root.getElementsByTagName(nameTag);
        for (int i = 0; i < serviceList.getLength(); i++) {
            Node service = serviceList.item(i);
            NodeList serviceData = service.getChildNodes();
            for (int j = 0; j < serviceData.getLength(); j++) {
                Node data = serviceData.item(j);
                if (data.getNodeType() == Node.ELEMENT_NODE) {
                    if (data.getNodeName() != null && data.getNodeName().equals("deviceType")) {
                        dev.setDevType(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("friendlyName")) {
                        dev.setName(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("manufacturer")) {
                        dev.setManufacturer(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("manufacturerURL")) {
                        dev.setManufacturerURL(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("modelDescription")) {
                        dev.setModelDesc(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("modelName")) {
                        dev.setModelName(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("modelURL")) {
                        dev.setModelURL(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("modelNumber")) {
                        dev.setModelNumber(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("serialNumber")) {
                        dev.setSerialNumber(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("UDN")) {
                        dev.setUdn(data.getTextContent());
                    } else if (data.getNodeName() != null && data.getNodeName().equals("iconList")) {
                        Node dataContent = data.getFirstChild();
                        if (dataContent.hasChildNodes() && dataContent.getChildNodes().getLength() > 4) {
                            dev.addIconList(dataContent.getChildNodes().item(4).getTextContent());
                        }
                    }
                }
            }
        }
        return dev;
    }

    private void parseServicesFromXML(org.w3c.dom.Document doc, String nameTag) {
        org.w3c.dom.Document xmldoc = doc;
        org.w3c.dom.Element root = xmldoc.getDocumentElement();
        NodeList serviceList = root.getElementsByTagName(nameTag);
        srvList = new LinkedList<>();
        for (int i = 0; i < serviceList.getLength(); i++) {
            Node service = serviceList.item(i);
            Servicios avt = new AVTransportService();
            Servicios cms = new ConnectionManagerService();
            Servicios rcs = new RenderingControlService();
            Servicios ecp = new ECPService();
            Servicios dial = new DialService();
            Servicios rctrl = new RemoteControlService();
            Servicios mediar = new X_MS_MediaReceiverRegistrar();
            Servicios cdir = new ContentDirectoryService();
            NodeList serviceData = service.getChildNodes();
            for (int j = 0; j < serviceData.getLength(); j++) {
                Node data = serviceData.item(j);
                if (data.getNodeType() == Node.ELEMENT_NODE) {
                    //System.out.println("Bloque: " + data.getNodeName() + ": Content: " + data.getTextContent());
                    if (data.getNodeName().contains("serviceName")) {

                        if (data.getTextContent().contains("ecp")) {
                            ecp.setServiceName(data.getTextContent());
                        } else if (data.getTextContent().contains("dial")) {
                            dial.setServiceName(data.getTextContent());
                        } else if (data.getTextContent().contains("AVTransport")) {
                            avt.setServiceName(data.getTextContent());
                        } else if (data.getTextContent().contains("ConnectionManager")) {
                            cms.setServiceName(data.getTextContent());
                        } else if (data.getTextContent().contains("RenderingControl")) {
                            rcs.setServiceName(data.getTextContent());
                        } else if (data.getTextContent().contains("RemoteControl")) {
                            rctrl.setServiceName(data.getTextContent());
                        } else if (data.getTextContent().contains("ContentDirectory")) {
                            cdir.setServiceName(data.getTextContent());
                        } else if (data.getTextContent().contains("X_MS_MediaReceiverRegistrar")) {
                            mediar.setServiceName(data.getTextContent());
                        }
                    } else if (data.getNodeName().contains("serviceType")) {
                        if (data.getTextContent().contains("ecp")) {
                            ecp.setServiceType(data.getTextContent());
                        } else if (data.getTextContent().contains("dial")) {
                            dial.setServiceType(data.getTextContent());
                        } else if (data.getTextContent().contains("AVTransport")) {
                            avt.setServiceType(data.getTextContent());
                        } else if (data.getTextContent().contains("ConnectionManager")) {
                            cms.setServiceType(data.getTextContent());
                        } else if (data.getTextContent().contains("RenderingControl")) {
                            rcs.setServiceType(data.getTextContent());
                        } else if (data.getTextContent().contains("RemoteControl")) {
                            rctrl.setServiceType(data.getTextContent());
                        } else if (data.getTextContent().contains("ContentDirectory")) {
                            cdir.setServiceType(data.getTextContent());
                        } else if (data.getTextContent().contains("X_MS_MediaReceiverRegistrar")) {
                            mediar.setServiceType(data.getTextContent());
                        }
                    } else if (data.getNodeName().contains("serviceId")) {
                        if (data.getTextContent().contains("ecp")) {
                            ecp.setServiceId(data.getTextContent());
                        } else if (data.getTextContent().contains("dial")) {
                            dial.setServiceId(data.getTextContent());
                        } else if (data.getTextContent().contains("AVTransport")) {
                            avt.setServiceId(data.getTextContent());
                        } else if (data.getTextContent().contains("ConnectionManager")) {
                            cms.setServiceId(data.getTextContent());
                        } else if (data.getTextContent().contains("RenderingControl")) {
                            rcs.setServiceId(data.getTextContent());
                        } else if (data.getTextContent().contains("RemoteControl")) {
                            rctrl.setServiceId(data.getTextContent());
                        } else if (data.getTextContent().contains("ContentDirectory")) {
                            cdir.setServiceId(data.getTextContent());
                        } else if (data.getTextContent().contains("X_MS_MediaReceiverRegistrar")) {
                            mediar.setServiceId(data.getTextContent());
                        }
                    } else if (data.getNodeName().contains("SCPDURL")) {
                        if (data.getTextContent().contains("ecp")) {
                            ((ECPService) ecp).setSCPDURL(data.getTextContent());
                        } else if (data.getTextContent().contains("dial")) {
                            ((DialService) dial).setSCPDURL(data.getTextContent());
                        } else if (data.getTextContent().contains("AVTransport")) {
                            ((AVTransportService) avt).setSCPDURL(data.getTextContent());
                        } else if (data.getTextContent().contains("ConnectionManager")) {
                            ((ConnectionManagerService) cms).setSCPDURL(data.getTextContent());
                        } else if (data.getTextContent().contains("RenderingControl")) {
                            ((RenderingControlService) rcs).setSCPDURL(data.getTextContent());
                        } else if (data.getTextContent().contains("RemoteControl")) {
                            ((RemoteControlService) rctrl).setSCPDURL(data.getTextContent());
                        } else if (data.getTextContent().contains("ContentDirectory")) {
                            ((ContentDirectoryService) cdir).setSCPDURL(data.getTextContent());
                        } else if (data.getTextContent().contains("X_MS_MediaReceiverRegistrar")) {
                            ((X_MS_MediaReceiverRegistrar) mediar).setSCPDURL(data.getTextContent());
                        }
                    } else if (data.getNodeName().contains("controlURL")) {
                        if (data.getTextContent().contains("ecp")) {
                            ((ECPService) ecp).setControlURL(data.getTextContent());
                        } else if (data.getTextContent().contains("dial")) {
                            ((DialService) dial).setControlURL(data.getTextContent());
                        } else if (data.getTextContent().contains("AVTransport")) {
                            ((AVTransportService) avt).setControlURL(data.getTextContent());
                        } else if (data.getTextContent().contains("ConnectionManager")) {
                            ((ConnectionManagerService) cms).setControlURL(data.getTextContent());
                        } else if (data.getTextContent().contains("RenderingControl")) {
                            ((RenderingControlService) rcs).setControlURL(data.getTextContent());
                        } else if (data.getTextContent().contains("RemoteControl")) {
                            ((RemoteControlService) rctrl).setControlURL(data.getTextContent());
                        } else if (data.getTextContent().contains("ContentDirectory")) {
                            ((ContentDirectoryService) cdir).setControlURL(data.getTextContent());
                        } else if (data.getTextContent().contains("X_MS_MediaReceiverRegistrar")) {
                            ((X_MS_MediaReceiverRegistrar) mediar).setControlURL(data.getTextContent());
                        }
                    } else if (data.getNodeName().contains("eventSubURL")) {
                        if (data.getTextContent().contains("ecp")) {
                            ((ECPService) ecp).setEventSubURL(data.getTextContent());
                        } else if (data.getTextContent().contains("dial")) {
                            ((DialService) dial).setEventSubURL(data.getTextContent());
                        } else if (data.getTextContent().contains("AVTransport")) {
                            ((AVTransportService) avt).setEventSubURL(data.getTextContent());
                        } else if (data.getTextContent().contains("ConnectionManager")) {
                            ((ConnectionManagerService) cms).setEventSubURL(data.getTextContent());
                        } else if (data.getTextContent().contains("RenderingControl")) {
                            ((RenderingControlService) rcs).setEventSubURL(data.getTextContent());
                        } else if (data.getTextContent().contains("RemoteControl")) {
                            ((RemoteControlService) rctrl).setEventSubURL(data.getTextContent());
                        } else if (data.getTextContent().contains("ContentDirectory")) {
                            ((ContentDirectoryService) cdir).setEventSubURL(data.getTextContent());
                        } else if (data.getTextContent().contains("X_MS_MediaReceiverRegistrar")) {
                            ((X_MS_MediaReceiverRegistrar) mediar).setEventSubURL(data.getTextContent());
                        }
                    }
                }
            }
            addService(avt);
            addService(cms);
            addService(rcs);
            addService(dial);
            addService(ecp);
            addService(rctrl);
            addService(cdir);
            addService(mediar);
        }
    }

    public void addService(Servicios s) {
        if (s.isFilled()) {
            //System.out.println("Tipo: "+s.getServiceType()+": tiene todo? "+s.isFilled());
            srvList.add(s);
        }
    }

    public Devices getDeviceFromXML(String xml) {
        Devices dev = null;
        try {
            DocumentBuilder dbuild = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            //fichero temporal con la informacion del XML
            File fxml = genTempFile(xml);
            //genero dispositivo
            dev = parseDeviceFromXML(dbuild.parse(fxml), "device");
            //cargo los servicios
            parseServicesFromXML(dbuild.parse(fxml), "service");
            //almaceno los servicios en el dispositivo
            dev.setServiceList(srvList);
            //elimino el fichero XML creado
            fxml.delete();

        } catch (SAXException ex) {
            logs.addLog(Level.SEVERE, "SAX on Utils: " + ex.getMessage());
        } catch (IOException ex) {
            logs.addLog(Level.SEVERE, "IOE on Utils: " + ex.getMessage());
        } catch (ParserConfigurationException ex) {
            logs.addLog(Level.SEVERE, "Parser Conf on Utils: " + ex.getMessage());
        }
        return dev;
    }

    public String ByteConverter(byte[] dataIn) {
        String result = "";
        for (byte b : dataIn) {
            result += (char) b;
        }
        return result;
    }

    public NetworkInterface getMyNetworkInterface() throws SocketException {
        Enumeration<NetworkInterface> listNI = NetworkInterface.getNetworkInterfaces();
        while (listNI.hasMoreElements()) {
            NetworkInterface networkInterface = listNI.nextElement();
            if (networkInterface.isUp() && networkInterface.supportsMulticast()) {
                //System.out.println(networkInterface.toString());
                Enumeration<InetAddress> subIA = networkInterface.getInetAddresses();
                while (subIA.hasMoreElements()) {
                    ia = subIA.nextElement();
                    if (ia.isSiteLocalAddress()) {
                        ni = networkInterface;
                    }
                    //System.out.println(ni.getName()+" "+inetAddress.toString());
                }
            }
        }
        return ni;
    }

    public String getMyIP() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            logs.addLog(Level.SEVERE, "Unknown Host when getMyIP on Util Class: " + ex.getMessage());
        }
        return ip;
    }

    public LoggerHelper getLogs() {
        return logs;
    }

    public InetAddress getInetAddress() {
        return ia;
    }
}
