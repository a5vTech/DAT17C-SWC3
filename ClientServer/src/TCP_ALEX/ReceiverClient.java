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
        Boolean run = true;
        while (run) {
            try {
                InputStream input = socket.getInputStream();
                byte[] dataIn = new byte[1024];
                input.read(dataIn);
                String msgIn = new String(dataIn);

                msgIn = msgIn.trim();
                if (msgIn.equals("")) {
                    run = false;
                }

                if(msgIn.contains("LIST")){
                    String[] activeUsers = msgIn.split(" ");
                    String currentlyConnectedUsers = "Currently connected users: [";
                    currentlyConnectedUsers += activeUsers[1];
                    for (int i = 2; i <= activeUsers.length-1 ; i++) {
                        currentlyConnectedUsers += ", " +activeUsers[i];
                    }
                    System.out.println(currentlyConnectedUsers+"]");
                }else{
                    System.out.print(msgIn + "\n");
                }



            } catch (IOException e) {
//                e.printStackTrace();
            }


        }
    }
}
