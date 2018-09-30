package TCP_ALEX;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ReceiverClient implements Runnable {

    Socket socket;

    public ReceiverClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true){
            try {
                InputStream input = socket.getInputStream();
                byte[] dataIn = new byte[1024];
                input.read(dataIn);
                String msgIn = new String(dataIn);
                msgIn = msgIn.trim();
                System.out.print(msgIn+"\n");

            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }
}
