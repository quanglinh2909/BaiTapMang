package IRM;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public void initServer(int port) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.bind("atm",new UserManager());
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        Server server = new Server();
        server.initServer(1234);
    }
}
