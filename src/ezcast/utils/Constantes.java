/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezcast.utils;

import java.io.Serializable;

/**
 *
 * @author AyA
 */
public class Constantes implements Serializable{

    final static String ip = "192.168.0.112"; // change me
    final static int port = 50000;
    final static String uriPlay = "<u:Play xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID>0</InstanceID><Speed>1</Speed></u:Play>\n";
    final static String xmlHead = "<?xml version=\"1.0\"?>\n<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n<SOAP-ENV:Body>\n";
    final static String xmlFoot = "</SOAP-ENV:Body>\n</SOAP-ENV:Envelope>\n";
    final static String xmlGetPos = "<m:GetPositionInfo xmlns:m=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID xmlns:dt=\"urn:schemas-microsoft-com:datatypes\" dt:dt=\"ui4\">0</InstanceID></m:GetPositionInfo>\n";
    final static String xmlStop = "<u:Stop xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID>0</InstanceID></u:Stop>\n";
    final static String xmlPause = "<u:Pause xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID>0</InstanceID></u:Pause>\n";
    String xmlVolumen = "<u:SetVolume xmlns:u=\"urn:schemas-upnp-org:service:RenderingControl:1\"><InstanceID>0</InstanceID><Channel>Master</Channel><DesiredVolume>100</DesiredVolume></u:SetVolume>\n";
    final static String xmlGetTransportInfo = "<m:GetTransportInfo xmlns:m=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID xmlns:dt=\"urn:schemas-microsoft-com:datatypes\" dt:dt=\"ui4\">0</InstanceID></m:GetTransportInfo>";
    final static String xmlGetPositionInfo = "<m:GetPositionInfo xmlns:m=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID xmlns:dt=\"urn:schemas-microsoft-com:datatypes\" dt:dt=\"ui4\">0</InstanceID></m:GetPositionInfo>";
    final static String xmlGetProtocolInfo = "<m:GetProtocolInfo xmlns:m=\"urn:schemas-upnp-org:service:ConnectionManager:1\"/>";
    final static String xmlGetVolumen = "<m:GetVolume xmlns:m=\"urn:schemas-upnp-org:service:RenderingControl:1\"><InstanceID xmlns:dt=\"urn:schemas-microsoft-com:datatypes\" dt:dt=\"ui4\">0</InstanceID><Channel xmlns:dt=\"urn:schemas-microsoft-com:datatypes\" dt:dt=\"string\">Master</Channel></m:GetVolume>";
    final static String xmlGetMute = "<m:GetMute xmlns:m=\"urn:schemas-upnp-org:service:RenderingControl:1\"><InstanceID xmlns:dt=\"urn:schemas-microsoft-com:datatypes\" dt:dt=\"ui4\">0</InstanceID><Channel xmlns:dt=\"urn:schemas-microsoft-com:datatypes\" dt:dt=\"string\">Master</Channel></m:GetMute>";
    final static String SEARCH_TARGET = "urn:schemas-upnp-org:service:AVTransport:1";
    final static String sendMsg = "M-SEARCH * HTTP/1.1\r\nHOST:239.255.255.250:1900\r\nST:" + SEARCH_TARGET + "\r\nMX:2\r\nMAN:\"ssdp:alive\"\r\n\r\n";
    final static String AVTServiceType = "urn:schemas-upnp-org:service:AVTransport:1";
    final static String AVTServiceID = "urn:upnp-org:serviceId:AVTransport";
    final static String AVTScpd = "/AVTransport/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/scpd.xml";
    final static String AVTControl = "/AVTransport/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/control.xml";
    final static String AVTEvent = "/AVTransport/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/event.xml";
    final static String RenderingControlServiceType = "urn:schemas-upnp-org:service:RenderingControl:1";
    final static String RenderinfControlServiceID = "urn:upnp-org:serviceId:RenderingControl";
    final static String RenderingControlScpd = "/RenderingControl/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/scpd.xml";
    final static String RenderingControlControl = "/RenderingControl/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/control.xml";
    final static String RenderingControlEvent = "/RenderingControl/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/event.xml";
    final static String ConnectionManagerServiceType = "urn:schemas-upnp-org:service:ConnectionManager:1";
    final static String ConnectionManagerServiceID = "urn:upnp-org:serviceId:ConnectionManager";
    final static String ConnectionManagerScpd = "/ConnectionManager/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/scpd.xml";
    final static String ConnectionManagerControl = "/ConnectionManager/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/control.xml";
    final static String ConnectionManagerEvent = "/ConnectionManager/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/event.xml";

    public Constantes() {
    }

    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }
    
    public static String getUriPlay() {
        return uriPlay;
    }

    public static String getXmlHead() {
        return xmlHead;
    }

    public static String getXmlFoot() {
        return xmlFoot;
    }

    public static String getXmlGetPos() {
        return xmlGetPos;
    }

    public static String getXmlStop() {
        return xmlStop;
    }

    public static String getXmlPause() {
        return xmlPause;
    }

    public String getXmlVolumen() {
        return xmlVolumen;
    }

    public static String getXmlGetTransportInfo() {
        return xmlGetTransportInfo;
    }

    public static String getXmlGetPositionInfo() {
        return xmlGetPositionInfo;
    }

    public static String getXmlGetProtocolInfo() {
        return xmlGetProtocolInfo;
    }

    public static String getXmlGetVolumen() {
        return xmlGetVolumen;
    }

    public static String getXmlGetMute() {
        return xmlGetMute;
    }

    public static String getSEARCH_TARGET() {
        return SEARCH_TARGET;
    }

    public static String getSendMsg() {
        return sendMsg;
    }

    public void setXmlVolumen(int Volumen) {
        this.xmlVolumen = "<u:SetVolume xmlns:u=\"urn:schemas-upnp-org:service:RenderingControl:1\"><InstanceID>0</InstanceID><Channel>Master</Channel><DesiredVolume>"+Volumen+"</DesiredVolume></u:SetVolume>\n";
        //this.xmlVolumen = "<u:SetVolume xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\"><InstanceID>0</InstanceID><Channel>0</Channel><DesiredVolume>" + Volumen + "</DesiredVolume></u:SetVolume>\n";;
    }
    
     public void setMute(){
         this.xmlVolumen = "<u:SetVolume xmlns:u=\"urn:schemas-upnp-org:service:RenderingControl:1\"><InstanceID>0</InstanceID><Channel>Master</Channel><DesiredVolume>0</DesiredVolume></u:SetVolume>\n";
     }    

    public static String getAVTServiceType() {
        return AVTServiceType;
    }

    public static String getAVTServiceID() {
        return AVTServiceID;
    }

    public static String getAVTScpd() {
        return AVTScpd;
    }

    public static String getAVTControl() {
        return AVTControl;
    }

    public static String getAVTEvent() {
        return AVTEvent;
    }

    public static String getRenderingControlServiceType() {
        return RenderingControlServiceType;
    }

    public static String getRenderinfControlServiceID() {
        return RenderinfControlServiceID;
    }

    public static String getRenderingControlScpd() {
        return RenderingControlScpd;
    }

    public static String getRenderingControlControl() {
        return RenderingControlControl;
    }

    public static String getRenderingControlEvent() {
        return RenderingControlEvent;
    }

    public static String getConnectionManagerServiceType() {
        return ConnectionManagerServiceType;
    }

    public static String getConnectionManagerServiceID() {
        return ConnectionManagerServiceID;
    }

    public static String getConnectionManagerScpd() {
        return ConnectionManagerScpd;
    }

    public static String getConnectionManagerControl() {
        return ConnectionManagerControl;
    }

    public static String getConnectionManagerEvent() {
        return ConnectionManagerEvent;
    }
     
     
}

