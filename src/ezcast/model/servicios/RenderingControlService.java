
package ezcast.model.servicios;

import ezcast.model.Servicios;
import java.util.LinkedList;

/**
 *
 * @author AyA
 */
public class RenderingControlService extends Servicios {

    private LinkedList<String> actionList =new LinkedList<String>();
    private LinkedList<String> serviceStateTable= new LinkedList<String>();
    private String SCPDURL;
    private String controlURL;
    private String eventSubURL;

    public RenderingControlService(String SCPDURL, String controlURL, String eventSubURL, String serviceType, String serviceId, String serviceName) {
        super(serviceType, serviceId, serviceName);
        this.SCPDURL = SCPDURL;
        this.controlURL = controlURL;
        this.eventSubURL = eventSubURL;
    }

    public RenderingControlService() {
        this.SCPDURL = "";
        this.controlURL = "";
        this.eventSubURL = "";
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

    public void setSCPDURL(String SCPDURL) {
        this.SCPDURL = SCPDURL;
    }

    public void setControlURL(String controlURL) {
        this.controlURL = controlURL;
    }

    public void setEventSubURL(String eventSubURL) {
        this.eventSubURL = eventSubURL;
    }

    public LinkedList<String> getActionList() {
        return actionList;
    }

    public void addActionList(String actionList) {
        this.actionList.add(actionList);
    }

    public LinkedList<String> getServiceStateTable() {
        return serviceStateTable;
    }

    public void addServiceStateTable(String serviceStateTable) {
        this.serviceStateTable.add(serviceStateTable);
    }

    @Override
    public boolean isFilled() {
        if (!getControlURL().isEmpty() || !getEventSubURL().isEmpty() || !getSCPDURL().isEmpty()) {
            return true;
        }
        return false;
    }

}
