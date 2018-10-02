package TCP_ALEX;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by coag on 27-09-2018.
 */
public class TCPServer {
    static Set<String> usernames = new HashSet<>();
    static ArrayList<ServerT> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=============SERVER==============");

        final int PORT_LISTEN = 5656;
        Thread alive = new Thread(() -> {

            while (true) {
                ArrayList<Integer> clientsToRemove = new ArrayList<>();


                try {
                    int timeout = 60000; //Timeout = 1 min
                    Thread.sleep(10000);
                    if (clients.size() >= 1) {
                        for (int i = 0; i < clients.size(); i++) {
                            if (System.currentTimeMillis() - clients.get(i).lastHeartbeat > timeout) {

//
//                                String inactiveMsg = "You have been disconnected due to inactivity or loss of connection";
//                                byte[] dataToSend;
//                                OutputStream output = null;
//                                try {
//                                    output = clients.get(i).socket.getOutputStream();
//                                    dataToSend = inactiveMsg.getBytes();
//                                    output.write(dataToSend);
//                                    TCPServer.removeUser(clients.get(i).username);
//                                    if (System.currentTimeMillis() - clients.get(i).lastHeartbeat > timeout && clients.get(i).username == null) {
                                    System.out.println(clients);
                                    clientsToRemove.add(i);

//                                    clients.get(i).socket.close();
//                                    clients.remove(i);

// }


//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                            }


                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < clientsToRemove.size(); i++) {
                    int temp = clientsToRemove.get(i);
                    try {
                        String inactiveMsg = "You have been disconnected due to inactivity or loss of connection";
                        byte[] dataToSend;
                        OutputStream output = null;
                        output = clients.get(i).socket.getOutputStream();
                        dataToSend = inactiveMsg.getBytes();
                        output.write(dataToSend);

                        usernames.remove(clients.get(temp).getUsername());
                        clients.get(i).socket.close();
                        clients.remove(temp);
                        TCPServer.broadcast(null, "Currently connected users: " + usernames.toString());
                        System.out.println(clients);
                    } catch (IOException e) {
//                        e.printStackTrace();
                    }

                }


            }
        });
        alive.start();


        try {
            ServerSocket server = new ServerSocket(PORT_LISTEN);
            System.out.println("Server starting...\n");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client connected");
                String clientIp = socket.getInetAddress().getHostAddress();
                System.out.println("IP: " + clientIp);
                System.out.println("PORT: " + socket.getPort());
                ServerT thread = new ServerT(socket, clientIp);
                Thread client = new Thread(thread);
                clients.add(thread);
                client.start();


            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }


    public static Boolean addUser(String username) {
        return usernames.add(username);
    }

    public static Boolean removeUser(String username) {
        return usernames.remove(username);
    }

    public static Boolean removeClientSocket(ServerT client) {
        return clients.remove(client);
    }


    public static Set<String> getusers() {
        return usernames;
    }


    public static void broadcast(ServerT client, String msgToSend) {
        for (int i = 0; i < clients.size(); i++) {
            if (!clients.get(i).equals(client)) {
                try {
                    if(!clients.get(i).socket.isClosed()){
                        byte[] dataToSend;
                        Socket socket = clients.get(i).socket;
                        OutputStream output = socket.getOutputStream();
                        dataToSend = msgToSend.getBytes();
                        output.write(dataToSend);

                    }

                } catch (IOException e) {
//                    e.printStackTrace();
                }

            }
        }
    }
//    public static void broadcastActiveUsers(String msgToSend) {
//        for (int i = 0; i < clients.size(); i++) {
//
//            try {
//                byte[] activeUsers;
//                Socket socket = clients.get(i).socket;
//                OutputStream output = socket.getOutputStream();
//                activeUsers = msgToSend.getBytes();
//                output.write(activeUsers);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//
//
//            }
//        }
//    }
}