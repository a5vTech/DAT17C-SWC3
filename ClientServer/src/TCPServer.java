//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public TCPServer() {
    }

    public static void main(String[] var0) throws Exception {
        System.out.println("starting TCP Server main program");
        ServerSocket var3 = new ServerSocket(5656);
        System.out.println("we have a socket");
        BufferedReader var4 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("waiting for a connection");
        Socket var5 = var3.accept();
        BufferedReader var6 = new BufferedReader(new InputStreamReader(var5.getInputStream()));
        DataOutputStream var7 = new DataOutputStream(var5.getOutputStream());

        String var1 = var6.readLine();
        System.out.println("FROM CLIENT: " + var1);
        System.out.print("Please type your text: ");
        String var2 = var4.readLine();
        var7.writeBytes(var2 + '\n');
        var1 = var6.readLine();
        System.out.println("FROM CLIENT: " + var1);
        var5.close();
        var3.close();
    }
}
