package ezcast.model;

import com.google.gson.annotations.Expose;
import ezcast.model.servicios.AVTransportService;
import ezcast.model.servicios.ConnectionManagerService;
import ezcast.model.servicios.ContentDirectoryService;
import ezcast.model.servicios.DialService;
import ezcast.model.servicios.ECPService;
import ezcast.model.servicios.RemoteControlService;
import ezcast.model.servicios.RenderingControlService;
import ezcast.model.servicios.X_MS_MediaReceiverRegistrar;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author AyA
 */
public class Devices implements Serializable{
    /*
     <deviceType>urn:schemas-upnp-org:device:MediaRenderer:1</deviceType>
     <friendlyName>AnyCast-c720-DLNA-DMR</friendlyName>
     <manufacturer>HQ</manufacturer>
     <manufacturerURL>http://www.baidu.com/</manufacturerURL>
     <modelDescription>HQ Media Center - Media Renderer</modelDescription>
     <modelName>HQ Media Center</modelName>
     <modelNumber>v0.10</modelNumber>
     <modelURL>http://www.baidu.com/</modelURL>
     <UDN>uuid:3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf</UDN>
     <dlna:X_DLNADOC>DMR-1.50</dlna:X_DLNADOC>
     */

    @Expose(serialize = true, deserialize = true)
    private String devType;
    private String name;
    private String manufacturer;
    private String manufacturerURL;
    private String modelDesc;
    private String modelName;
    private String modelNumber;
    private String modelURL;
    private String serialNumber;
    private String udn;
    private String dlna_doc;
    @Expose(serialize = false, deserialize = true)
    private String host;
    private int port;
    private final String[] SoapActions;
    private LinkedList<String> iconList;
    private LinkedList<Servicios> serviceList;

    public Devices() {
        iconList = new LinkedList<>();
        serviceList = new LinkedList<>();
        this.SoapActions = new String[]{
            "AVTransport:1#SetAVTransportURI",
            "AVTransport:1#GetPositionInfo",
            "AVTransport:1#Play",
            "AVTransport:1#Pause",
            "AVTransport:1#Stop",
            "AVTransport:1#Next",
            "AVTransport:1#Previous",
            "AVTransport:1#Seek",
            "RenderingControl:1#SetVolume",
            "RenderingControl:1#GetVolume",
            "RenderingControl:1#GetMute",
            "ConnectionManager:1#GetProtocolInfo",
            "AVTransport:1#GetTransportInfo"};
    }

    public String[] getSoapActions() {
        return SoapActions;
    }

    public void addService(Servicios srv) {
        serviceList.add(srv);
    }

    public Servicios getServiceFromList(int pos) {
        return serviceList.get(pos);
    }

    public LinkedList<Servicios> getServiceList() {
        return serviceList;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturerURL() {
        return manufacturerURL;
    }

    public void setManufacturerURL(String manufacturerURL) {
        this.manufacturerURL = manufacturerURL;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getModelURL() {
        return modelURL;
    }

    public void setModelURL(String modelURL) {
        this.modelURL = modelURL;
    }

    public String getUdn() {
        return udn;
    }

    public void setUdn(String udn) {
        this.udn = udn;
    }

    public String getDlna_doc() {
        return dlna_doc;
    }

    public void setDlna_doc(String dlna_doc) {
        this.dlna_doc = dlna_doc;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setServiceList(LinkedList<Servicios> lls) {
        this.serviceList = lls;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void addIconList(String iconUrl) {
        iconList.add(iconUrl);
    }

    public LinkedList getIconList() {
        return iconList;
    }

    public void printAllData() {
        System.out.println("Device Type:    " + getDevType() + "\n"
                + "Familiar Name:   " + getName() + "\n"
                + "Manufacturer:    " + getManufacturer() + "\n"
                + "Manufacturer URL:    " + getManufacturerURL() + "\n"
                + "Model Name:  " + getModelName() + "\n"
                + "Model Description:   " + getModelDesc() + "\n"
                + "Model Number:    " + getModelNumber() + "\n"
                + "Model URL:   " + getModelURL() + "\n"
                + "DLNA:    " + getDlna_doc() + "\n"
                + "UDN: " + getUdn() + "\n"
                + "URL Server:  " + getHost() + ":" + getPort());
        System.out.println("Icon List");
        for (String str : iconList) {
            System.out.println(getHost() + ":" + getPort() +"/"+ str);
        }
        System.out.println("\nService List:");
        int count = 0;
        for (Servicios service : serviceList) {            
            printData(count,service);
            count++;
        }
    }
    
    void printData(int count, Servicios service){
        if(service instanceof AVTransportService){
            System.out.println("Service No: " + count + "\n"
                    + "Service Type: " + ((AVTransportService)service).getServiceType() + "\n"
                    + "Service ID: " + ((AVTransportService)service).getServiceId() + "\n"
                    + "SCPURL: " + ((AVTransportService)service).getSCPDURL() + "\n"
                    + "Control URL: " + ((AVTransportService)service).getControlURL() + "\n"
                    + "Event URL: " + ((AVTransportService)service).getEventSubURL());
        }else if(service instanceof ConnectionManagerService){
            System.out.println("Service No: " + count + "\n"
                    + "Service Type: " + ((ConnectionManagerService)service).getServiceType() + "\n"
                    + "Service ID: " + ((ConnectionManagerService)service).getServiceId() + "\n"
                    + "SCPURL: " + ((ConnectionManagerService)service).getSCPDURL() + "\n"
                    + "Control URL: " + ((ConnectionManagerService)service).getControlURL() + "\n"
                    + "Event URL: " + ((ConnectionManagerService)service).getEventSubURL());
        }else if(service instanceof RenderingControlService){
            System.out.println("Service No: " + count + "\n"
                    + "Service Type: " + ((RenderingControlService)service).getServiceType() + "\n"
                    + "Service ID: " + ((RenderingControlService)service).getServiceId() + "\n"
                    + "SCPURL: " + ((RenderingControlService)service).getSCPDURL() + "\n"
                    + "Control URL: " + ((RenderingControlService)service).getControlURL() + "\n"
                    + "Event URL: " + ((RenderingControlService)service).getEventSubURL());
        }else if(service instanceof DialService){
            System.out.println("Service No: " + count + "\n"
                    + "Service Type: " + ((DialService)service).getServiceType() + "\n"
                    + "Service ID: " + ((DialService)service).getServiceId() + "\n"
                    + "SCPURL: " + ((DialService)service).getSCPDURL() + "\n"
                    + "Control URL: " + ((DialService)service).getControlURL() + "\n"
                    + "Event URL: " + ((DialService)service).getEventSubURL());
        }else if(service instanceof ECPService){
            System.out.println("Service No: " + count + "\n"
                    + "Service Type: " + ((ECPService)service).getServiceType() + "\n"
                    + "Service ID: " + ((ECPService)service).getServiceId() + "\n"
                    + "SCPURL: " + ((ECPService)service).getSCPDURL() + "\n"
                    + "Control URL: " + ((ECPService)service).getControlURL() + "\n"
                    + "Event URL: " + ((ECPService)service).getEventSubURL());
        }else if(service instanceof RemoteControlService){
                        System.out.println("Service No: " + count + "\n"
                    + "Service Type: " + ((RemoteControlService)service).getServiceType() + "\n"
                    + "Service ID: " + ((RemoteControlService)service).getServiceId() + "\n"
                    + "SCPURL: " + ((RemoteControlService)service).getSCPDURL() + "\n"
                    + "Control URL: " + ((RemoteControlService)service).getControlURL() + "\n"
                    + "Event URL: " + ((RemoteControlService)service).getEventSubURL());

        }else if(service instanceof X_MS_MediaReceiverRegistrar){
                        System.out.println("Service No: " + count + "\n"
                    + "Service Type: " + ((X_MS_MediaReceiverRegistrar)service).getServiceType() + "\n"
                    + "Service ID: " + ((X_MS_MediaReceiverRegistrar)service).getServiceId() + "\n"
                    + "SCPURL: " + ((X_MS_MediaReceiverRegistrar)service).getSCPDURL() + "\n"
                    + "Control URL: " + ((X_MS_MediaReceiverRegistrar)service).getControlURL() + "\n"
                    + "Event URL: " + ((X_MS_MediaReceiverRegistrar)service).getEventSubURL());

        }else if(service instanceof ContentDirectoryService){
                        System.out.println("Service No: " + count + "\n"
                    + "Service Type: " + ((ContentDirectoryService)service).getServiceType() + "\n"
                    + "Service ID: " + ((ContentDirectoryService)service).getServiceId() + "\n"
                    + "SCPURL: " + ((ContentDirectoryService)service).getSCPDURL() + "\n"
                    + "Control URL: " + ((ContentDirectoryService)service).getControlURL() + "\n"
                    + "Event URL: " + ((ContentDirectoryService)service).getEventSubURL());

        }
    }

}
