package org.example;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5000;
        String filename = "file_to_send.txt";

        // Create the file with content "hello world" if it doesn't exist
        File file = new File(filename);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("I AM FROM CLIENT");
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
                return;
            }
        }

        // Sending the file to the server
        try (
                Socket socket = new Socket(serverAddress, port);
                FileInputStream fileInputStream = new FileInputStream(filename);
                OutputStream outputStream = socket.getOutputStream();
        ) {
            System.out.println("Connected to server.");

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

