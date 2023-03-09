package ezcast.model;

import java.io.Serializable;

/**
 *
 * @author AyA
 */
public abstract class Servicios implements Serializable {

    //@SerializedName("serviceType")
    //@Expose
    private String serviceType;
    //@SerializedName("serviceId")
    //@Expose
    private String serviceId;
    private String serviceName;

    public Servicios(String serviceType, String serviceId, String serviceName) {
        this.serviceType = serviceType;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public Servicios() {
        this.serviceType = "";
        this.serviceId = "";
        this.serviceName = "";
    }


    public String getServiceType() {
        return serviceType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }   

    public abstract String getSCPDURL();

    public abstract String getControlURL();

    public abstract String getEventSubURL();

    public abstract boolean isFilled();

}
