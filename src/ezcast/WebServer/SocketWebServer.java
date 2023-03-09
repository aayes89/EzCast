/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezcast.WebServer;

import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AyA
 */
public class SocketWebServer {

    private String hostname;
    private int port;
    ServerSocket ss;
    Socket sock;
    PrintWriter pw;
    OutputStream os;
    BufferedReader br;
    private boolean isStarted;
    Thread task;
    File myFile;

    public SocketWebServer(int port) {
        try {
            this.hostname = InetAddress.getLocalHost().getHostAddress();
            this.port = port;
            ss = new ServerSocket(port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketWebServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SocketWebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SocketWebServer() {
        try {
            hostname = InetAddress.getLocalHost().getHostAddress();
            port = 8080;
            ss = new ServerSocket(port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketWebServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SocketWebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopServer() {
        isStarted = false;
        task.interrupt();
    }

    public void startServer(File upFile) {
        this.myFile = upFile;
        task = new Thread(new Runnable() {

            @Override
            public void run() {
                initiateServer();
            }
        });
        task.start();
    }

    private void initiateServer() {
        isStarted = true;
        String response = "<html>\n"
                + "<head><title>Multimedia Web Streaming</title></head>\n"
                + "<body>\n"
                + "<h1>Web Server start success.\n<br>If you see this message</h1>" + "<h1>Listening on port: " + port + "</h1>"
                + "</body>\n"
                + "</html>";
        try {
            while (isStarted) {
                sock = ss.accept();
                pw = new PrintWriter(sock.getOutputStream(), true);
                os = sock.getOutputStream();
                br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String s = br.readLine();
                System.out.println(s);
                while ("\r\n".equals(br.readLine())) {
                    if ("GET / HTTP/1.1".equals(s)) {
                        pw.println(response);
                        os.write("HTTP/1.1 200 OK".getBytes());
                        os.write("Connection: close".getBytes());
                        os.write("Content-Type: text/html, text/plain".getBytes());
                        os.write(("Content-Length: " + hostname.length()).getBytes());
                        os.write(hostname.getBytes());
                        os.write(response.getBytes());
                    } else if ("GET /video.mp4 HTTP/1.1".equals(s)) {
                        if (myFile != null) {
                            byte[] dataFile = new byte[(int) myFile.length()];
                            FileInputStream fis = new FileInputStream(myFile);
                            fis.read(dataFile);
                            fis.close();
                            os.write(dataFile);
                            os.close();
                        }
                    } else {
                        os.write("HTTP/1.1 404 Not found".getBytes());
                        os.write("Connection: close".getBytes());
                    }
                    os.flush();
                    pw.flush();
                    os.close();
                    pw.close();
                }
                if (isStarted == false) {
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketWebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isIsStarted() {
        return isStarted;
    }

}
