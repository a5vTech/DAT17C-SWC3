package TCP_ALEX;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by coag on 27-09-2018.
 */
public class TCPServer {
    static Set<String> usernames = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("=============SERVER==============");

        final int PORT_LISTEN = 5656;


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
                client.start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Boolean addUser(String username) {
       return usernames.add(username);
    }


    public static Set<String> getusers() {
        return usernames;
    }
}