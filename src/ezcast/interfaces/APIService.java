/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezcast.interfaces;

import java.io.Serializable;

/**
 *
 * @author AyA
 */
public abstract class APIService implements Serializable{
    
    public String getMainConfigs() {
        return "/";
    }

    public String getVideoURL(){
        return "/video.mp4";
    }
    
    public String getAVTConfig() {
        return "/AVTransport/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/scpd.xml";
    }

    public String getAVTControlConfig() {
        return "/AVTransport/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/control.xml";
    }

    public String getAVTEventConfig() {
        return "/AVTransport/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/event.xml";
    }

    public String getCMConfig() {
        return "/ConnectionManager/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/scpd.xml";
    }

    public String getCMControlConfig() {
        return "/ConnectionManager/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/control.xml";
    }

    public String getCMEventConfig() {
        return "/ConnectionManager/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/event.xml";
    }

    public String getRCConfig() {
        return "/RenderingControl/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/scpd.xml";
    }

    public String getRCControlConfig() {
        return "/RenderingControl/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/control.xml";
    }

    public String getRCEventConfig() {
        return "/RenderingControl/3e1388f0-1dd2-11b2-83b7-4c0fc7209ecf/event.xml";
    }

}
