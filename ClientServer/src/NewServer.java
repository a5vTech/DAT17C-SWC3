import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NewServer {

    public static void main(String[] args) {
        int port = 5656;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("===========NewServer===========");

            while (true) {
                System.out.println("Waiting for client to connect...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket);
                Thread thread = new Thread(new ServerThreads());
                thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void handleClientSocket(Socket clientSocket) {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();

            for (int i = 0; i < 20; i++) {
                Thread.sleep(1000);
                outputStream.write("Hello client!\n".getBytes());
            }
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
