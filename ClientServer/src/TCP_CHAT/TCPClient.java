package TCP_CHAT;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class TCPClient {
    static String username;
    static String serverIp;
    static String serverPort;
    static Socket socket;
    static Scanner inputFromKeyboard = new Scanner(System.in);

    public static void main(String[] args) {
        makeConnection();

    }

    public static void makeConnection() {
        System.out.println("=============Client=============");
        System.out.print("Please enter the ip of the server: ");
        serverIp = inputFromKeyboard.nextLine();
        System.out.print("Please enter the servers port: ");
        serverPort = inputFromKeyboard.next();
        System.out.print("Please enter your desired username: ");
        inputFromKeyboard.nextLine();
        username = inputFromKeyboard.nextLine();
        System.out.print("Trying to connect to the server");
        //Adding connecting animation
        loadingAnimation();


        try {
            socket = new Socket(serverIp, Integer.parseInt(serverPort));
            System.out.println("\nConnected to the server successfully");
            System.out.println("====================================");
            System.out.print("\nTrying to connect to chat");
            loadingAnimation();

            BufferedReader receiveFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream sendToServer = new DataOutputStream(socket.getOutputStream());

            sendToServer.writeBytes("JOIN " + username + ", " + serverIp + ":" + serverPort + '\n');


            String reply = receiveFromServer.readLine();
            if(reply.equals("J_OK")){
                System.out.println("You joined the chat!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void loadingAnimation() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(".");
        }
    }

}