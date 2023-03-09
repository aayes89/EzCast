/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezcast.model.servicios;

import ezcast.model.Servicios;

/**
 *
 * @author AyA
 */
public class X_MS_MediaReceiverRegistrar extends Servicios {

    private String SCPDURL;
    private String controlURL;
    private String eventSubURL;

    public X_MS_MediaReceiverRegistrar(String SCPDURL, String controlURL, String eventSubURL, String serviceType, String serviceId, String serviceName) {
        super(serviceType, serviceId, serviceName);
        this.SCPDURL = SCPDURL;
        this.controlURL = controlURL;
        this.eventSubURL = eventSubURL;
    }

    public X_MS_MediaReceiverRegistrar() {
        this.SCPDURL = "";
        this.controlURL = "";
        this.eventSubURL = "";
    }

    public String getSCPDURL() {
        return SCPDURL;
    }

    public void setSCPDURL(String SCPDURL) {
        this.SCPDURL = SCPDURL;
    }

    public String getControlURL() {
        return controlURL;
    }

    public void setControlURL(String controlURL) {
        this.controlURL = controlURL;
    }

    public String getEventSubURL() {
        return eventSubURL;
    }

    public void setEventSubURL(String eventSubURL) {
        this.eventSubURL = eventSubURL;
    }

    @Override
    public boolean isFilled() {
        if(!getSCPDURL().isEmpty()||!getControlURL().isEmpty()||!getEventSubURL().isEmpty())
            return true;
        return false;
    }
    
}
