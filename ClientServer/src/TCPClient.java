import java.io.*;
import java.net.*;
import java.util.Scanner;

class TCPClient {


    public static void main(String args[]) throws Exception {
        int clientNumber = 0;
        System.out.println("starting TCPClient main");
        String sentence;

        Scanner inFromUser = new Scanner(System.in);

        System.out.println("trying to connect");
        //   Socket clientSocket = new Socket("10.111.176.99", 5656);
        //   Socket clientSocket = new Socket("10.111.176.99", 5656); //SEAN
        Socket clientSocket = new Socket("127.0.0.1", 5656); //JESPER
        System.out.println("we are connected");

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        System.out.println("Please enter the following cmd to join the server!");
        System.out.println("The syntax is: JOIN <<username>>, <<ip>>:<<port>>");
        boolean sentinel = false;
        while (!sentinel) {
            System.out.print("Please type your text: ");
            sentence = inFromUser.nextLine();
            outToServer.writeBytes(sentence +"'\n'");

            if (sentence.equalsIgnoreCase("quit")) {
                sentinel = true;
                System.out.println("QUITTING");
                clientSocket.close();
            } else {
                sentence = inFromServer.readLine();
                System.out.println("FROM SERVER: " + sentence);

                if (sentence.equalsIgnoreCase("quit")) {
                    sentinel = true;
                    System.out.println("QUITTING");
                    clientSocket.close();
                }
            }
        }

    }

}