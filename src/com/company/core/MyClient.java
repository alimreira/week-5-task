package com.company.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyClient extends Thread{

    static void handleClient(Socket client) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line).append("\r\n");
        }

        String request = requestBuilder.toString();
        System.out.println(request);

        String path = request.split("\r\n")[0].split(" ")[1];

        Path filePath = MyServer.getFilePath(path);
        System.out.println(filePath);
        if (Files.exists(filePath)) {
            String contentType = MyServer.guessContentType(filePath);
            MyServer.sendResponse(client, "200 OK", contentType, Files.readAllBytes(filePath));
        } else {
            byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
            MyServer.sendResponse(client, "404 Not Found", "text/html", notFoundContent);
        }
    }
}
