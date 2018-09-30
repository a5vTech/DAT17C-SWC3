package TCP_ALEX;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by coag on 27-09-2018.
 */
public class TCPClient {
    static Socket socket;

    public static void main(String[] args) {
        System.out.println("=============CLIENT==============");

        Scanner sc = new Scanner(System.in);
        System.out.print("What is the IP for the server (type 0 for localhost): ");
        String ipToConnect = args.length >= 1 ? args[0] : sc.nextLine();

        System.out.print("What is the PORT for the server: ");
        int portToConnect = args.length >= 2 ? Integer.parseInt(args[1]) : sc.nextInt();

        System.out.print("What is your username: ");
        String username = sc.next();

        final int PORT_SERVER = portToConnect;
        final String IP_SERVER_STR = ipToConnect.equals("0") ? "127.0.0.1" : ipToConnect;
        System.out.println("\nConnecting...");
        System.out.println("SERVER IP: " + IP_SERVER_STR);
        System.out.println("SERVER PORT: " + PORT_SERVER + "\n");
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(IP_SERVER_STR);
            //Create socket with servers ip and port
            socket = new Socket(ip, PORT_SERVER);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReceiverClient receiver = new ReceiverClient(socket);
        Thread read = new Thread(receiver);

        Boolean joinedChat = false;
        while (true) {
            try {


                //Creaate input and output readers
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                while (!joinedChat){
                    String joinMsg = "JOIN " + username + "," + IP_SERVER_STR + ":" + PORT_SERVER;
                    //Convert message to bytes
                    byte[] dataToSend = joinMsg.getBytes();
                    //Send data to server
                    output.write(dataToSend);

                    //Prepare byte array to store incoming message
                    byte[] dataIn = new byte[1024];
                    //Read data from server into the byte array
                    input.read(dataIn);
                    //Convert data to msg
                    String msgIn = new String(dataIn);
                    //Remove trailing blank spaces
                    msgIn = msgIn.trim();

                    if (!msgIn.equals("J_OK")) {
                        System.out.println(msgIn.substring(5));
                        System.out.print("Please try another name: ");
                        username = sc.next();

                    } else {
                        System.out.println("You have joined the chat! ");
                        joinedChat = true;
                    }
                    //Start reading from server

                    if(joinedChat){
                        read.start();
                    }
                }



                //Message to send
                sc = new Scanner(System.in);

                String msgToSend = sc.nextLine();
                if (!msgToSend.equals("QUIT") && !msgToSend.startsWith("IMAV")) {
                    msgToSend = "DATA " + username + ": " + msgToSend;

                    //Convert message to bytes
                    byte[] dataToSend = msgToSend.getBytes();
                    //Send data to server

                    output.write(dataToSend);
                } else if (msgToSend.equals("QUIT")) {
                    System.out.println("Shutting down chat");
                    //Convert message to bytes
                    byte[] dataToSend = msgToSend.getBytes();
                    //Send data to server

                    output.write(dataToSend);
                    System.exit(0);
                }


//                //Prepare byte array to store incoming message
//                byte[] dataIn = new byte[1024];
//                //Read data from server into the byte array
//                input.read(dataIn);
//                //Convert data to msg
//                String msgIn = new String(dataIn);
//                //Remove trailing blank spaces
//                msgIn = msgIn.trim();
//
//
//                System.out.println("IN -->" + msgIn + "<--");

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkUsername(String username){
        Scanner inputUsername = new Scanner(System.in);
        boolean verified = false;

        if(username.length() <= 12 && !username.contains(" ")){
            verified = true;
        }

        while (!verified){
            System.out.println("Username does not meet the requirements!");
            System.out.println("Max length is: 12\nCan contain letters, digits, '-' and '_'");
            System.out.print("New username: ");
            username = inputUsername.next();
        }


        if(username.length() <= 12 && !username.contains(" ")){
            verified = true;
        }

    }
}