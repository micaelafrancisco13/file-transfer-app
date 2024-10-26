package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;

public class Server {
    public static void main(String[] args) {
        int port = 5000;

        // Define the path for the received files directory
        String directoryPath = "C:/Users/u734131/Documents/Repositories/Playground/file-transfer-app/src/main/java/org/example/receivedfiles";
        File directory = new File(directoryPath);

        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            var ignored = directory.mkdirs();
        }

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                InputStream inputStream = clientSocket.getInputStream();
        ) {
            System.out.println("Client connected.");
            System.out.println("Receiving file content:");

            // Generate a unique filename with the current UTC timestamp
            String timestamp = Instant.now().toString().replace(":", "-");
            String receivedFilename = directoryPath + "/received_file_" + timestamp + ".txt";

            // Open FileOutputStream for the generated filename
            try (FileOutputStream fileOutputStream = new FileOutputStream(receivedFilename)) {
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) > 0) {
                    // Print received content to console
                    String content = new String(buffer, 0, bytesRead);
                    System.out.print(content);

                    // Write the content to the file
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("\nFile received and saved as " + receivedFilename);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
