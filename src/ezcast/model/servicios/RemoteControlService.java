package ezcast.model.servicios;

import ezcast.model.Servicios;

/**
 *
 * @author AyA
 */
public class RemoteControlService extends Servicios {

    private String SCPDURL;
    private String controlURL;
    private String eventSubURL;

    public RemoteControlService(String serviceName, String SCPDURL, String controlURL, String eventSubURL, String serviceType, String serviceId) {
        super(serviceType, serviceId, serviceName);        
        this.SCPDURL = SCPDURL;
        this.controlURL = controlURL;
        this.eventSubURL = eventSubURL;
    }

    public RemoteControlService() {        
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
        if (!getControlURL().isEmpty() || !getEventSubURL().isEmpty() || !getSCPDURL().isEmpty()) {
            return true;
        }
        return false;
    }   

}
