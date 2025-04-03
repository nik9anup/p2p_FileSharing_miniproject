package com.fileshare.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FileTransferHandler {
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 4096;
    private static final Map<String, String> activeTransfers = new HashMap<>(); // Tracks ongoing transfers

    public static void sendFile(String filePath, String peerIP) {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            System.err.println("Error: File does not exist or is a directory.");
            return;
        }

        try (Socket socket = new Socket(peerIP, PORT);
             FileInputStream fileInputStream = new FileInputStream(file);
             BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
             DataOutputStream dataOut = new DataOutputStream(out)) {

            System.out.println("Sending file: " + file.getName());

            // Notify peers about file transfer
            activeTransfers.put(peerIP, file.getName());

            // Send file metadata first
            dataOut.writeUTF(file.getName());
            dataOut.writeLong(file.length());  // Send file size

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            long bytesSent = 0;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                bytesSent += bytesRead;
                System.out.printf("Progress: %.2f%%\r", (bytesSent * 100.0) / file.length());
            }
            out.flush();

            System.out.println("\nFile sent successfully to " + peerIP);
            activeTransfers.remove(peerIP); // Transfer complete

        } catch (IOException e) {
            System.err.println("Error sending file: " + e.getMessage());
        }
    }

    public static void startFileReceiver(String saveDirectory) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Listening for incoming file transfers on port " + PORT + "...");

                while (true) {
                    try (Socket socket = serverSocket.accept();
                         DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                         BufferedInputStream in = new BufferedInputStream(socket.getInputStream())) {

                        String fileName = dataIn.readUTF();
                        long fileSize = dataIn.readLong();  // Read file size
                        File file = new File(saveDirectory, fileName);

                        System.out.println("Receiving file: " + file.getAbsolutePath());
                        activeTransfers.put(socket.getInetAddress().getHostAddress(), fileName);

                        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                             BufferedOutputStream fileOut = new BufferedOutputStream(fileOutputStream)) {

                            byte[] buffer = new byte[BUFFER_SIZE];
                            long bytesReceived = 0;
                            int bytesRead;

                            while (bytesReceived < fileSize && (bytesRead = in.read(buffer)) != -1) {
                                fileOut.write(buffer, 0, bytesRead);
                                bytesReceived += bytesRead;
                                System.out.printf("Progress: %.2f%%\r", (bytesReceived * 100.0) / fileSize);
                            }
                            fileOut.flush();

                            System.out.println("\nFile received successfully: " + file.getAbsolutePath());
                            activeTransfers.remove(socket.getInetAddress().getHostAddress());
                        }
                    } catch (IOException e) {
                        System.err.println("Error receiving file: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Error starting file receiver: " + e.getMessage());
            }
        }).start();
    }

    public static boolean isTransferInProgress(String peerIP) {
        return activeTransfers.containsKey(peerIP);
    }
}
