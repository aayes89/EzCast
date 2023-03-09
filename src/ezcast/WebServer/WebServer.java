/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezcast.WebServer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AyA
 */
public class WebServer implements Runnable, Serializable {

    private ServerSocket server;
    private Socket sock;
    private BufferedReader br;
    private PrintWriter pw;
    private BufferedOutputStream bos;
    private String urlData;
    private static final int PORT = 8080;
    private static final boolean verbose = true;

    public WebServer(String data) {
        this.urlData = data;
        br = null;
        pw = null;
        bos = null;
        startServer();
    }

    private void startServer() {
        try {
            server = new ServerSocket(PORT);
            sock = server.accept();
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData() {
        try {
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pw = new PrintWriter(sock.getOutputStream());
            bos = new BufferedOutputStream(sock.getOutputStream());
            String firstLine = br.readLine();
            StringTokenizer parse = new StringTokenizer(firstLine);
            String method = parse.nextToken().toUpperCase();
            String fileRequest = parse.nextToken().toLowerCase();
            if (!method.equals("GET") && !method.equals("HEAD")) {
                if (verbose) {
                    System.out.println("501 Not Implemented");
                }
                String contentMimeType = "text/html";
                pw.println("HTTP/1.1 501 Not Implemented");
                pw.println("Server: Java Server for EzCast Ver. 1.0");
                pw.println("Date: " + new Date());
                pw.println("Content-type: " + contentMimeType);
                pw.println("Content-length: 0");
                pw.println();
                pw.flush();
                bos.flush();
            } else {
                if (fileRequest.endsWith("/")) {
                    fileRequest += "/";
                }
                File upFile = new File(urlData);
                int fLength = (int) upFile.length();
                String content = getContentType(fileRequest);

                if (method.equals("GET")) {
                    byte[] fileData = readFileData(upFile, fLength);

                    pw.println("HTTP/1.1 200 OK");
                    pw.println("Server: Java Server for EzCast Ver. 1.0");
                    pw.println("Date: " + new Date());
                    pw.println("Content-type: " + content);
                    pw.println("Content-length: " + fLength);
                    pw.println();
                    String simplePage = "<html>\n"
                            + "<head><title>Multimedia Web Streaming</title></head>\n"
                            + "<body>\n"
                            + "<video controls>\n"
                            + "<a href=\"data:video/mp4;base64,";

                    for (int i = 0; i < fileData.length; i++) {
                        simplePage += (char) fileData[i];
                    }
                    simplePage += "\">\n"
                            + "</a>\n"
                            + "</body>\n"
                            + "</html>";
                    pw.println(simplePage);
                    pw.flush();
                    bos.write(fileData, 0, fLength);
                    bos.flush();
                }
                if (verbose) {
                    System.out.println("File " + fileRequest + " of type " + content + " returned");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                pw.close();
                bos.close();
                sock.close();
            } catch (Exception e) {
                System.out.println("Error closing stream: " + e.getMessage());
            }
        }
    }

    private byte[] readFileData(File data, int length) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[length];
        try {
            fileIn = new FileInputStream(data);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
        return fileData;
    }

    private String getContentType(String fRequest) {
        if (fRequest.endsWith(".htm") || fRequest.endsWith(".html")) {
            return "text/html";
        } else if (fRequest.endsWith(".json")) {
            return "application/json";
        } else if (fRequest.endsWith(".gif") || fRequest.endsWith(".jpg") || fRequest.endsWith(".jpeg") || fRequest.endsWith(".bmp") || fRequest.endsWith(".png")) {
            return "application/image";
        } else if (fRequest.endsWith(".mp4") || fRequest.endsWith(".mpg") || fRequest.endsWith(".avi") || fRequest.endsWith(".mkv") || fRequest.endsWith(".3gp") || fRequest.endsWith(".webm")) {
            return "application/video";
        } else if (fRequest.endsWith(".mp3") || fRequest.endsWith(".mp2") || fRequest.endsWith(".wav") || fRequest.endsWith(".ogg")) {
            return "application/sound";
        } else {
            return "text/plain";
        }
    }

    @Override
    public void run() {
        loadData();
    }
}
