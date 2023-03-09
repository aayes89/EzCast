package ezcast.WebServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import ezcast.utils.LoggerHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author AyA
 */
public class SimpleWebServer implements Serializable {

    private HttpServer server;
    private InetSocketAddress isa;
    private static final int port = 8080;
    private boolean isStarted;
    private LoggerHelper log;

    public SimpleWebServer() {
        isStarted = false;
        log = new LoggerHelper();
        try {
            isa = new InetSocketAddress(port);
            server = HttpServer.create(isa, port);
        } catch (IOException ex) {
            log.addLog(Level.SEVERE, "IOE on SimpleWebServer Constructor method: " + ex.getMessage());
        }
    }

    public void startServer(String urlLink) {
        System.out.println("Server started at " + port);
        try {
            server.createContext("/", new HttpHandler() {

                @Override
                public void handle(HttpExchange he) throws IOException {
                    if (!urlLink.isEmpty()) {
                        String file = "";
                        if (urlLink.endsWith("mp4") || urlLink.endsWith("avi") || urlLink.endsWith("mpg")) {
                            String[] spl = urlLink.split("/");
                            file = spl[spl.length - 1];
                        }
                        String response = "<html>\n"
                                + "<head><title>Multimedia Web Streaming</title></head>\n"
                                + "<body>\n"
                                + "<h1>Web Server start success.\n<br>If you see this message</h1>" + "<h1>Listening on port: " + port + "</h1>"
                                + "<h3>Open video link belown</h3>\n"
                                + "<a href=\"" + urlLink + "\">" + file + "</a>"
                                + "</body>\n"
                                + "</html>";

                        he.sendResponseHeaders(200, response.length());
                        OutputStream os = he.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } else {
                        String response = "<html>\n"
                                + "<head><title>Multimedia Web Streaming</title></head>\n"
                                + "<body>\n"
                                + "<h1>Web Server start success.\n<br>If you see this message</h1>" + "<h1>Listening on port: " + port + "</h1>"
                                + "</body>\n"
                                + "</html>";
                        he.sendResponseHeaders(200, response.length());
                        OutputStream os = he.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                }
            });
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            isStarted = true;
        } catch (Exception ex) {
            log.addLog(Level.SEVERE, "E on startServer with URL method: " + ex.getMessage());
        } finally {
            stopServer();
        }
    }

    public void startServer(File upFile) {

        System.out.println("server started at " + port);
        try {
            /*server.createContext("/", new HttpHandler() {

             @Override
             public void handle(HttpExchange he) throws IOException {
             if (upFile != null) {
             String response;
             response = "<html>\n"
             + "<head><title>Multimedia Web Streaming</title></head>\n"
             + "<body>\n"
             + "<h1>Web Server start success.\n<br>If you see this message</h1>" + "<h1>Listening on port: " + port + "</h1>"
             + "<h3>Open video link belown</h3>\n"
             + "<a href=\"/video.mp4\">" + upFile.getName() + "</a>"
             + "</body>\n"
             + "</html>";

             he.sendResponseHeaders(200, response.length());
             OutputStream os = he.getResponseBody();
             os.write(response.getBytes());
             os.close();
             } else {
             String response = "<html>\n"
             + "<head><title>Multimedia Web Streaming</title></head>\n"
             + "<body>\n"
             + "<h1>Web Server start success.\n<br>If you see this message</h1>" + "<h1>Listening on port: " + port + "</h1>"
             + "</body>\n"
             + "</html>";
             he.sendResponseHeaders(200, response.length());
             OutputStream os = he.getResponseBody();
             os.write(response.getBytes());
             os.close();
             }
             }
             });*/
            server.createContext("/video.mp4", new HttpHandler() {

                @Override
                public void handle(HttpExchange he) throws IOException {
                    if (upFile != null) {
                        he.sendResponseHeaders(200, upFile.length());
                        OutputStream os = he.getResponseBody();
                        byte[] dataFile = new byte[(int) upFile.length()];
                        FileInputStream fis = new FileInputStream(upFile);
                        fis.read(dataFile);
                        fis.close();
                        os.write(dataFile);
                        os.close();
                    }
                }
            });
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            isStarted = true;
        } catch (Exception ex) {
            log.addLog(Level.SEVERE, "E on startServer with File method: " + ex.getMessage());
        } finally {
            stopServer();
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void stopServer() {
        if (isStarted == true) {
            isStarted = false;
            server.stop(0);
        }
    }

    public LoggerHelper getLogs() {
        return log;
    }
}
