package TCP_CHAT;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class ServerThread extends Thread {
    TCPServer server;
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



        Boolean sentinel = false;
        while (!sentinel) {
            try {
                String sentence;
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                sentence = inFromClient.readLine();
                if (sentence.equalsIgnoreCase("quit")) {
                    sentinel = true;
                    System.out.println("QUITTING");
                    socket.close();
                }else{
                    System.out.println("\nFrom client: " + sentence);
                    TCPServer.broadcast(sentence,this);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


//        String clientMsg = "";
//        String cmd = "";
//        String username = "";
//        Scanner msgScan = new Scanner(clientMsg);
//        String[] arr1 = clientMsg.split(",");
//
//        while (!clientMsg.equals("QUIT")) {
//
//            try {
//                clientMsg = receiveFromClient.readLine();
//                if (clientMsg.contains("JOIN")) {
//                    cmd = clientMsg.substring(0, 4);
//                    username = clientMsg.substring(4, clientMsg.length() - 1);
//                    System.out.println(cmd);
//                }
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                switch (cmd) {
//
//                    case "JOIN":
//                        String[] arr = clientMsg.split(",");
//                        sendToClient.writeBytes("You got the id: " + clientNumber + System.lineSeparator() + "Welcome to the server, " + username + "YOU JOINED THE CHANNEL!" + '\n');
//
//                        cmd = "";
//                        break;
//
//                    default:
//                        System.out.println("From client: " + clientMsg);
//                        System.out.print("Reply to client: ");
//                        sendToClient.writeBytes(inFromUser.nextLine() + '\n');
//                        break;
//
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }



}
