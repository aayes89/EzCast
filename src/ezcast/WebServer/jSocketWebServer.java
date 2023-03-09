package ezcast.WebServer;

import ezcast.utils.LoggerHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JFileChooser;

/**
 *
 * @author AyA
 */
public class jSocketWebServer {

    static Writer writer;
    private static ServerSocket serverConnect;
    private static final int PORT = 8081;
    static int id = 0;
    private static LoggerHelper logs;
    private WorkerThread wt;
    private Thread t;

    public jSocketWebServer() {
        logs = new LoggerHelper();
    }

    private static void serverSocketCreate() {
        try {
            serverConnect = new ServerSocket(PORT);
        } catch (IOException e) {
            logs.addLog(Level.SEVERE, "IOE on serverSocketCreate method: " + e.getMessage());
        }
        System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
    }

    private static void logFileCreate() {
        File logFile = new File("log.txt");
        boolean result = false;
        try {
            result = Files.deleteIfExists(logFile.toPath());
            if (result) {
                logFile.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile), StandardCharsets.UTF_8));
        } catch (IOException e) {
            logs.addLog(Level.SEVERE, "IOE on logFileCreate: " + e.getMessage());
        }
    }

    public void startServer() throws IOException {
        serverSocketCreate();
        logFileCreate();
        while (true) {
            Socket s = serverConnect.accept();
            wt = new WorkerThread(s);
            t = new Thread(wt);
            t.start();
            System.out.println("Thread number is: " + id);
            if(wt.isIsFinished())
                stopServer();
        }
    }

    public void stopServer() {
        if (t != null) {
            if (t.isAlive()) {
                t.interrupt();
                System.out.println("Thread " + jSocketWebServer.id + " stoped");
            }
        }
    }

    public LoggerHelper getLogs() {
        return logs;
    }
}

class WorkerThread implements Runnable {

    private Socket s;
    private BufferedReader in;
    private DataOutputStream out;
    private LoggerHelper logs;

    private boolean isFinished;
    private static String MIME_TYPE;
    private static final String SUCCESS_HEADER = "HTTP/1.1 200 OK\r\n";
    private static final String ERROR_HEADER = "HTTP/1.1 404 Not Found\r\n";
    private static final String OUTPUT_HEADERS = "Content-Type: " + MIME_TYPE + "\r\nContent-Length: ";
    private static final String SEND_CHUNK_DATA = "HTTP/1.0 200 OK\r\n"
            + "Connection: close\r\n"
            + "Max-Age: 0\r\n"
            + "Expires: 0\r\n"
            + "Cache-Control: no-store, no-cache, must-revalidate, pre-check=0, post-check=0, max-age=0\r\n"
            + "Pragma: no-cache\r\n"
            + "Content-Type: multipart/mixed;\r\n"
            + "boundary=stream\r\n"
            + "\r\n"
            + "--stream\r\n"
            + OUTPUT_HEADERS;
    private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";
    private static final String FILE_NOT_FOUND = "<html>\n<head>\n<title>\nError\n</title>\n</head>\n<body>\n<p>\n<h1>404-File Not Found</h1>\n</p>\n</body>\n</html>";

    public WorkerThread(Socket s) {
        this.s = s;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            logs.addLog(Level.SEVERE, "IOE: " + e.getMessage());
        }
        isFinished = false;
        jSocketWebServer.id++;
    }

    private void closeConnection() throws IOException {
        isFinished = true;
        if (out != null) {
            out.flush();
            out.close();
        }
        if(in!=null)
            in.close();
        if(s!=null)
            s.close();        
        //jAPItest.writer.close();
    }

    public boolean isIsFinished() {
        return isFinished;
    }   

    private void sendListBytesAsData(List<Byte> data) throws IOException {
        if (!data.isEmpty()) {
            out.writeBytes(SUCCESS_HEADER);
            out.writeBytes(OUTPUT_HEADERS);
            out.write(data.size());
            out.writeBytes(OUTPUT_END_OF_HEADERS);
            //chunk time
            byte[] barray = new byte[Byte.MAX_VALUE];
            int chunks = data.size() / barray.length;
            while (chunks > 0) {
                for (int i = 0; i < barray.length; i++) {
                    barray[i] = data.get(i);
                }
                out.write(barray);
                chunks--;
            }
        }
    }

    private void sendBytesAsData(byte[] data) throws IOException {
        if (data != null) {
            out.writeBytes(SUCCESS_HEADER);
            out.writeBytes(OUTPUT_HEADERS + data.length + OUTPUT_END_OF_HEADERS);
            //out.write(data.length);
            //out.writeBytes(OUTPUT_END_OF_HEADERS);
            out.write(data);
        }
    }

    private void sendStringAsData(String sData) throws IOException {
        if (!sData.isEmpty()) {
            out.writeBytes(SUCCESS_HEADER);
            out.writeBytes(OUTPUT_HEADERS);
            out.write(sData.length());
            out.writeBytes(OUTPUT_END_OF_HEADERS);
            out.writeBytes(sData);
        }
    }

    private void sendErrorMessage() throws IOException {
        out.writeBytes(ERROR_HEADER);
        out.writeBytes(OUTPUT_HEADERS);
        out.write(FILE_NOT_FOUND.length());
        out.writeBytes(OUTPUT_END_OF_HEADERS);
        out.writeBytes(FILE_NOT_FOUND);
    }

    private void setMimeType(String fileType) {
        if (fileType.equals("html")) {
            MIME_TYPE = "text/html";
        } else if (fileType.equals("mp4")) {
            MIME_TYPE = "video/mp4";
        } else if (fileType.equals("mpg")) {
            MIME_TYPE = "application/video";
        } else if (fileType.equals("mp3")) {
            MIME_TYPE = "application/sound";
        } else if (fileType.equals("ogg")) {
            MIME_TYPE = "application/sound";
        } else if (fileType.equals("png")) {
            MIME_TYPE = "image/png";
        } else if (fileType.equals("pdf")) {
            MIME_TYPE = "application/pdf";
        } else if (fileType.equals("jpg")) {
            MIME_TYPE = "image/jpg";
        } else if (fileType.equals("jpeg")) {
            MIME_TYPE = "image/jpeg";
        } else if (fileType.equals("bmp")) {
            MIME_TYPE = "image/bmp";
        } else if (fileType.equals("tiff")) {
            MIME_TYPE = "image/tiff";
        } else if (fileType.equals("tif")) {
            MIME_TYPE = "image/tiff";
        } else if (fileType.equals("default")) {
            MIME_TYPE = "text/html";
        }
    }

    private byte[] readFileIntoByteArray(File file) throws IOException {
        byte[] data = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(data);
        fileInputStream.close();
        return data;
    }

    private void writeToLogFile(String message, String statusCode, int fileSize) throws IOException {
        jSocketWebServer.writer.write(InetAddress.getLocalHost().getHostAddress() + "--" + "[" + new Date().toString() + "] \"" + message + "\" " + statusCode + " " + fileSize);
        jSocketWebServer.writer.write("\r\n");
        jSocketWebServer.writer.flush();
    }

    private String readRequest() throws IOException {
        return in.readLine();
    }

    private int contentLength() throws IOException {
        String str;
        int postDataI = -1;
        while ((str = readRequest()) != null) {
            if (str.isEmpty()) {
                break;
            }
            final String contentHeader = "Content-Length: ";
            if (str.contains(contentHeader)) {
                postDataI = Integer.parseInt(str.substring(contentHeader.length()));
            }
        }
        return postDataI;
    }
    @Override
    public void run() {
        try {
            String index = "<html>\n"
                    + "<head><title>Socket Web</title></head>\n"
                    + "<body>\n"
                    + "<p><h1>WebSocket Main Page</h1></p>\n"
                    + "</body>\n"
                    + "</html>";
            String message = readRequest();
            System.out.println(message);
            if (message.equals("GET / HTTP/1.1")) {
                writeToLogFile(message, "200", message.length());
                setMimeType("text/plain");
                sendStringAsData(index);
            } else if (message.equals("GET /video.mp4 HTTP/1.1")) {
                File upFile = null;
                byte[] dataFile;
                JFileChooser chooser = new JFileChooser("D:\\Peliculas");
                int opc = chooser.showOpenDialog(null);
                if (opc == JFileChooser.APPROVE_OPTION) {
                    upFile = chooser.getSelectedFile();
                    dataFile = readFileIntoByteArray(upFile);
                    sendBytesAsData(dataFile);
                }
            } else {
                String response = "HTTP/1.1 501 Not Implemented\n"
                        + "Server: " + InetAddress.getLocalHost().getHostAddress() + "\n"
                        + "Date: " + new Date() + "\n"
                        + "Content-type: \"text/plain\"\n"
                        + "Content-Length: 0";
                writeToLogFile(response, "501", response.length());
                sendStringAsData(response);
            }
            closeConnection();
        } catch (IOException | NullPointerException e) {
            logs.addLog(Level.SEVERE, "IOE on run method: " + e.getMessage() + "\nCause: " + e.getCause().toString());
        }
    }

}
