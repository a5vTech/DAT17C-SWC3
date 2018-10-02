package TCP_ALEX;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.Date;

public class ServerT implements Runnable {
    Socket socket;
    String clientIp;
    String username;
    long lastHeartbeat = 0;


    public ServerT(Socket socket, String ip) {
        this.socket = socket;
        this.clientIp = ip;
        lastHeartbeat = System.currentTimeMillis();
    }


    @Override
    public void run() {


        while (!socket.isClosed()) {
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

                if (msgIn.length() >= 4){
                    switch (msgIn.substring(0, 4)) {

                        //Join chat
                        case "JOIN":
                            int commaIndex = msgIn.indexOf(",");

                            username = msgIn.substring(5, commaIndex);

                            if (TCPServer.addUser(username)) {
                                msgToSend = "J_OK";

                            } else {
                                msgToSend = "J_ER 5:A user with that name already exists!";
                            }

                            dataToSend = msgToSend.getBytes();
                            output.write(dataToSend);

                            if (msgToSend.equals("J_OK")) {
                                String activeUsers = "LIST";

                               for (String username: TCPServer.usernames){
                                   activeUsers += " " +username;
                               }



                                msgToSend = activeUsers;
                                TCPServer.broadcast(this,msgToSend);
                                dataToSend = msgToSend.getBytes();
                                output.write(dataToSend);
                            }

                            break;

                        //Message
                        case "DATA":
                            //To log commands run towards the server
                            System.out.println("IN -->" + msgIn + "<--");
                            TCPServer.broadcast(this, msgIn.substring(5));

//                        msgToSend = "SERVER: [sender:" + clientIp + " ]: " + msgIn.substring(5);
//                        dataToSend = msgToSend.getBytes();
//                        output.write(dataToSend);
                            break;
                        //I am alive
                        case "IMAV":
                            lastHeartbeat = System.currentTimeMillis();
                            System.out.println(msgIn);
                            break;

                        //Quit chat
                        case "QUIT":
                            TCPServer.removeUser(username);
                            TCPServer.removeClientSocket(this);
                            String activeUsers = "LIST";

                            for (String username: TCPServer.usernames){
                                activeUsers += " " +username;
                            }

                            msgToSend = activeUsers;
                            TCPServer.broadcast(this,msgToSend);
                            dataToSend = msgToSend.getBytes();
                            output.write(dataToSend);
                            socket.close();

                            break;

                        default:

                            break;

                    }
            }

//                System.out.println("USERS CONNECTED: ");
//                System.out.println(TCPServer.getusers());

                } catch(SocketException ex){

                } catch(IOException ex){
//                    ex.printStackTrace();
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
