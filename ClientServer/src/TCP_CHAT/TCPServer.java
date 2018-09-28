package TCP_CHAT;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {
    final static int PORT_IN = 5656;
    final static int PORT_OUT = 5657;
    static int clientNumber;


    public static void main(String[] args) {
        try {
            System.out.println("Welcome to the server!");
            System.out.println("Establishing socket");
            ServerSocket server = new ServerSocket(PORT_IN);
            System.out.println("Socket created");
            System.out.println("Waiting for connections!");

            while (true) {
                Socket connectedSocket = server.accept();
                clientNumber++;
                Thread thread = new Thread(new ServerThread(connectedSocket, clientNumber));
                thread.start();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


class ServerThread implements Runnable {
    Socket socket = null;
    final static int PORT_IN = 5555;
    final static int PORT_OUT = 5556;
    Scanner inFromUser = new Scanner(System.in);
    BufferedReader receiveFromClient = null;
    DataOutputStream sendToClient = null;
    int clientNumber = 0;


    public ServerThread(Socket socket, int clientNumber) {
        this.clientNumber = clientNumber;
        try {
            receiveFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sendToClient = new DataOutputStream(socket.getOutputStream());
            this.socket = socket;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String clientMsg = "";
        String cmd = "";
        String username = "";
        Scanner msgScan = new Scanner(clientMsg);
        String[] arr1 = clientMsg.split(",");

        while (!clientMsg.equals("QUIT")) {

            try {
                clientMsg = receiveFromClient.readLine();
                if (clientMsg.contains("JOIN")) {
                    cmd = clientMsg.substring(0, 4);
                    username = clientMsg.substring(4, clientMsg.length() - 1);
                    System.out.println(cmd);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                switch (cmd) {

                    case "JOIN":
                        String[] arr = clientMsg.split(",");
                        sendToClient.writeBytes("You got the id: " + clientNumber + System.lineSeparator()+"Welcome to the server, " + username +"YOU JOINED THE CHANNEL!" + '\n');

                        cmd = "";
                        break;

                    default:
                        System.out.println("From client: " + clientMsg);
                        System.out.print("Reply to client: ");
                        sendToClient.writeBytes(inFromUser.nextLine() + '\n');
                        break;


                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
