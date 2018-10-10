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

    }
}
