package ezcast.model.servicios;

import ezcast.model.Servicios;

/**
 *
 * @author AyA
 */
public class ECPService extends Servicios {

    private String SCPDURL;
    private String controlURL;
    private String eventSubURL;

    public ECPService(String SCPDURL, String controlURL, String eventSubURL, String serviceType, String serviceId, String serviceName) {
        super(serviceType, serviceId, serviceName);
        this.SCPDURL = SCPDURL;
        this.controlURL = controlURL;
        this.eventSubURL = eventSubURL;
    }


    public ECPService() {
        this.SCPDURL = "";
        this.controlURL = "";
        this.eventSubURL = "";
    }

    public void setSCPDURL(String SCPDURL) {
        this.SCPDURL = SCPDURL;
    }

    public void setControlURL(String controlURL) {
        this.controlURL = controlURL;
    }

    public void setEventSubURL(String eventSubURL) {
        this.eventSubURL = eventSubURL;
    }

    @Override
    public String getSCPDURL() {
        return SCPDURL;
    }

    @Override
    public String getControlURL() {
        return controlURL;
    }

    @Override
    public String getEventSubURL() {
        return eventSubURL;
    }

    public void printAllDataService() {
        System.out.println("Service: " + getServiceId());
        System.out.println("Service: " + getServiceType());
        System.out.println("Service: " + getSCPDURL());
        System.out.println("Service: " + getControlURL());
        System.out.println("Service: " + getEventSubURL());
    }

    @Override
    public boolean isFilled() {
        if (!getControlURL().isEmpty() || !getEventSubURL().isEmpty() || !getSCPDURL().isEmpty()) {
            return true;
        }
        return false;
    }
}
