package DeThiLan1_2008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void initServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            Socket socket = serverSocket.accept();
            OneConnection oneConnection = new OneConnection(socket);
            oneConnection.start();
        }
    }

    public static void main(String[] args) throws IOException {
        initServer(12345);
    }
}
