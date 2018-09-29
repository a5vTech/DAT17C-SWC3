package TCP_ALEX;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerT implements Runnable {
    Socket socket;
    String clientIp;
    String username;


    public ServerT(Socket socket, String ip) {
        this.socket = socket;
        this.clientIp = ip;
    }


    @Override
    public void run() {
        while (true) {
            try {
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();

                //Receive msg from client
                byte[] dataIn = new byte[1024];
                input.read(dataIn);
                String msgIn = new String(dataIn);
                msgIn = msgIn.trim();

                String msgToSend = "";
                byte[] dataToSend;

                switch (msgIn.substring(0, 4)) {

                    //Join chat
                    case "JOIN":
//TODO check for correct join message and for duplicate username
                        int commaIndex = msgIn.indexOf(",");

                        username = msgIn.substring(5, commaIndex);

                        if (TCPServer.addUser(username)) {
                            msgToSend = "J_OK";
                        } else {
                            msgToSend = "J_ER 5:A user with that name already exists!";
                        }

                        dataToSend = msgToSend.getBytes();
                        output.write(dataToSend);

                        break;

                    //Message
                    case "DATA":
                        //To log commands run towards the server
                        System.out.println("IN -->" + msgIn + "<--");
                        //TODO Add broadcast method (So other clients can receive messages)


                        msgToSend = "SERVER: [sender:" + clientIp + " ]: " + msgIn.substring(5);
                        dataToSend = msgToSend.getBytes();
                        output.write(dataToSend);
                        break;
                    //I am alive
                    case "IMAV":

                        break;

                    //Quit chat
                    case "QUIT":
                        socket.close();
                        break;

                    default:

                        break;

                }


//                System.out.println("USERS CONNECTED: ");
//                System.out.println(TCPServer.getusers());

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public synchronized String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
