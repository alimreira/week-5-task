package com.company.core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyServer{

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {

        System.out.println("Server Starting...");

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Listening to port: " + serverSocket.getLocalPort());
            while (true) try (Socket client = serverSocket.accept()) {
                MyClient myClient = new MyClient();
                myClient.start();
                MyClient.handleClient(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException {
        OutputStream clientOutput = client.getOutputStream();
        clientOutput.write(("HTTP/1.1 \r\n" + status).getBytes());
        clientOutput.write(("ContentType: " + contentType + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(content);
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        client.close();
    }

    public static Path getFilePath(String path) {
        System.out.println("path is" + path);

        Path location;
        switch (path) {
            case "/":
                location = Paths.get("./src/com/company/resources/index.html");
                break;
            case "/json":
                location = Paths.get("./src/com/company/resources/package.json");
                break;
            default:
                location = Paths.get(" ");
                break;
        }
        System.out.println(location);
        return location;
    }

    public static String guessContentType(Path filePath) throws IOException {
        return Files.probeContentType(filePath);
    }
}