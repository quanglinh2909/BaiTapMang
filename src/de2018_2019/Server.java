package de2018_2019;

import java.io.FileNotFoundException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public void initServer(int port) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.bind("child",new ChildManager());
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        Server server =new Server();
        server.initServer(12345);
    }
}
