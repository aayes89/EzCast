package ezcast;

import ezcast.view.JFEzCast;

/**
 *
 * @author AyA
 */
public class EzCast {

    public static void main(String[] args) {

        JFEzCast v = new JFEzCast();
        v.setVisible(true);
        /*
         //String linkDev = "http://192.168.0.112:50000";
         //http://192.168.0.163:2869/upnpho
         //Controller class
         Control ctrl = new Control();

         //Web servers
         SimpleWebServer sws = new SimpleWebServer();
        
         //*---- Lookup devices from LAN network ----*
         //ctrl.lookupDevices();  
         //*---- Testing url ----*
         ctrl.loadVideoLink("http://misc.commonsware.com/ed_hd_512kb.mp4");
         //ctrl.loadVideoLink("http://192.168.0.163:8080/video.mp4");
         //ctrl.loadVideoLink("http://192.168.0.163/video.mp4");
         ctrl.addUrlToDevLost("http://192.168.0.150:8060");
         //ctrl.addUrlToDevLost("http://192.168.0.147:2869/upnpho");
         //ctrl.addUrlToDevLost("http://192.168.0.163:2869/upnpho");
         //ctrl.addUrlToDevLost(linkDev);

         //*---- Loading data from external device ----*
         ctrl.loadMainConfig();
         ctrl.getDevices().getFirst().printAllData();
         /*---- Device for testing ----*/
        /*Devices dev = new Devices();
         dev.setHost(ctrl.getHost(linkDev));
         dev.setPort(ctrl.getPort(linkDev));
         //Adding services to test device
         dev.addService(new AVTransportService(Constantes.getAVTScpd(), Constantes.getAVTControl(), Constantes.getAVTEvent(), Constantes.getAVTServiceType(), Constantes.getAVTServiceID(), "AVTransportService"));
        dev.addService(new ConnectionManagerService(Constantes.getConnectionManagerScpd(), Constantes.getConnectionManagerControl(), Constantes.getConnectionManagerEvent(), Constantes.getConnectionManagerServiceType(), Constantes.getConnectionManagerServiceID(), "ConnectionManagerService"));
        dev.addService(new RenderingControlService(Constantes.getRenderingControlScpd(), Constantes.getRenderingControlControl(), Constantes.getRenderingControlEvent(), Constantes.getRenderingControlServiceType(), Constantes.getRenderingControlServiceID(), "RenderingControlService"));
        
         /*---- Adding device for testing ----*/
        //ctrl.addDevice(dev);*/

        /*---- Search for video file ----*/
        /*JFileChooser cho = new JFileChooser();
         int res = cho.showOpenDialog(null);
         if (res == JFileChooser.APPROVE_OPTION) {
         //Getting selected file
         File faccepted = cho.getSelectedFile();
         System.out.println("Path: "+faccepted.getAbsolutePath());

         System.out.println("Starting Web Server...");
         //Starting a webserver to stream video file
         sws.startServer(faccepted);

         //Getting the first device fetched in the list of devices 
         Devices captured = ctrl.getDevices().getFirst();
         captured.printAllData();

         //*---- Sending data to device for streaming ---*
         //Stop last reproduction
         String data = "";
         data = Constantes.getXmlHead() + Constantes.getXmlStop() + Constantes.getXmlFoot();
         //System.out.println(data);
         ctrl.SendDataToDevice(data, captured.getSoapActions()[4], captured);

         //Send a new URL to load
         data = Constantes.getXmlHead() + ctrl.getUriLoad() + Constantes.getXmlFoot();
         //System.out.println(data);
         ctrl.SendDataToDevice(data, captured.getSoapActions()[0], captured);

         //Play action
         data = Constantes.getXmlHead() + Constantes.getUriPlay() + Constantes.getXmlFoot();
         //System.out.println(data);
         ctrl.SendDataToDevice(data, captured.getSoapActions()[2], captured);

         /*
         //Get Volumen
         data = Constantes.getXmlHead() + Constantes.getXmlGetVolumen() + Constantes.getXmlFoot();
         System.out.println(data);
         ctrl.SendDataToDevice(data, captured.getSoapActions()[9], captured);

         data = Constantes.getXmlHead() + Constantes.getXmlGetVolumen() + Constantes.getXmlFoot();
         System.out.println(data);
         ctrl.SendDataToDevice(data, captured.getSoapActions()[8], captured);
             
         System.out.println("Información cargada en EzCast\n"
         + "Espere mientras EzCast procesa los datos enviados");
         }*/
    }
}
