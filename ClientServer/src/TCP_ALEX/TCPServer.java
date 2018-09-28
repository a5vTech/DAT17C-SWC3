package TCP_ALEX;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by coag on 27-09-2018.
 */
public class TCPServer {
    public static void main(String[] args) {
        System.out.println("=============SERVER==============");

        final int PORT_LISTEN = 5656;


        try {
            ServerSocket server = new ServerSocket(PORT_LISTEN);

            System.out.println("Server starting...\n");


            Socket socket = server.accept();
            System.out.println("Client connected");
            String clientIp = socket.getInetAddress().getHostAddress();
            System.out.println("IP: " + clientIp);
            System.out.println("PORT: " + socket.getPort());

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            byte[] dataIn = new byte[1024];
            input.read(dataIn);
            String msgIn = new String(dataIn);
            msgIn = msgIn.trim();


            System.out.println("IN -->" + msgIn + "<--");

            String msgToSend = "SERVER: [sender:" + clientIp + " ]: " + msgIn;
            byte[] dataToSend = msgToSend.getBytes();
            output.write(dataToSend);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}