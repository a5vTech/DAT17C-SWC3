package TCP_CHAT;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TCPServer {
    final static int PORT_IN = 5656;
    final static int PORT_OUT = 5657;
    static int clientNumber;
    static ArrayList<ServerThread> userConnections = new ArrayList<>();


    public static void main(String[] args) {

        try {
            System.out.println("Welcome to the server!");
            System.out.println("Establishing socket");
            ServerSocket server = new ServerSocket(PORT_IN);
            System.out.println("Socket created");
            System.out.println("Waiting for connections!");
//            Socket clientSocket = server.accept();

//
//            BufferedReader receiveFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            DataOutputStream sendToClient = new DataOutputStream(clientSocket.getOutputStream());
//
//            String msgFromClient = receiveFromClient.readLine();
//
//            if(msgFromClient.startsWith("JOIN")){
//                sendToClient.writeBytes("J_OK");
//            }else if (msgFromClient.startsWith("DATA")){
//                System.out.println(msgFromClient);
//            }
//
//            System.out.println(msgFromClient);





            while (true) {
                Socket connectedSocket = server.accept();
                System.out.println("Client connected: " + connectedSocket);

                clientNumber++;
                ServerThread thread = new ServerThread(connectedSocket, clientNumber);
                userConnections.add(thread);
                thread.start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void broadcast(String msg, ServerThread excludeClient) {
        Socket broadcastMsg = excludeClient.socket;
        try {

            DataOutputStream out = excludeClient.sendToClient;
            for (ServerThread client : userConnections) {
                if (client != excludeClient) {
                    out.writeBytes(msg);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

