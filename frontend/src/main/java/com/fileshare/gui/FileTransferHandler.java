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

public class FileTransferHandler {

    private static final int PORT = 5000;

    public static void sendFile(String filePath, String peerIP) {
        try (Socket socket = new Socket(peerIP, PORT);
             FileInputStream fileInputStream = new FileInputStream(filePath);
             BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())) {

            File file = new File(filePath);
            byte[] buffer = new byte[4096];
            int bytesRead;

            System.out.println("Sending file: " + file.getName());
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeUTF(file.getName());

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startFileReceiver(String saveDirectory) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Waiting for incoming file transfers...");

                while (true) {
                    try (Socket socket = serverSocket.accept();
                         DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                         BufferedInputStream in = new BufferedInputStream(socket.getInputStream())) {

                        String fileName = dataIn.readUTF();
                        File file = new File(saveDirectory, fileName);

                        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;

                            while ((bytesRead = in.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, bytesRead);
                            }

                            System.out.println("File received: " + file.getAbsolutePath());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
