package IRM;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {
    IUserManager server;

    public Client(int post) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(post);
        server = (IUserManager) registry.lookup("atm");
    }

    public void startClient() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chao mung ban den voi ngan hang TPBank");
        String response, request;
        while (true) {
            request = scanner.nextLine().trim();
            if (request.equalsIgnoreCase("quit")) break;
            response = analysisRequest(request);
            System.out.println(response);
        }
        scanner.close();
    }

    private String analysisRequest(String request) throws RemoteException {
        String t[] = request.replace(" +", " ").split(" ");
        if (t.length <= 0) return "vui long nhap lenh";
        switch (t[0]) {
            case "get":
                return getBalance(t);
            case "r":
                return withdraw(t);
            case "g":
                return deposit(t);
            case "c":
                return transfer(t);
            case "i":
                return report(t);

            default:
                return " sai cu phap";
        }
    }

    private String report(String[] t) throws RemoteException {
        if(t.length != 3) return "Sai cu phap";
        Account account  = new Account(t[1],t[2]);
        List<History> account2=  server.report(account);
        if(account2 == null) return "Tai khoan hoac pin sai";
        return account2.toString();
    }

    private String transfer(String[] t) {
        if(t.length != 5) return "Sai cu phap";
        try {
            int tien = Integer.parseInt(t[4]);
            Account account = new Account(t[1],t[2]);
            Account account2 = new Account(t[3]);
            Account account3= server.transfer(account,account2,tien);
            if(account3 == null) return "loi";
            return "gui tien thanh cong";

        }catch (Exception e){
            return "tien phai la so";

        }
    }

    private String deposit(String[] t) {
        if(t.length != 4) return "Sai cu phap";

        try {
            int tien = Integer.parseInt(t[3]);
            Account account = new Account(t[1],t[2]);
            Account account2= server.deposit(account,tien);
            if(account2 == null) return "loi rut tien!!!";
            return "gui tien thanh cong";

        }catch (Exception e){
            return "tien phai la so";

        }
    }

    private String withdraw(String[] t) {
        if(t.length != 4) return "Sai cu phap";

        try {
            int tien = Integer.parseInt(t[3]);
            Account account = new Account(t[1],t[2]);
            Account account2= server.withdraw(account,tien);
            if(account2 == null) return "tai khoan hoac pin sai";
            return "rut tien thanh cong";

        }catch (Exception e){
            return "tien phai la so";

        }
    }

    private String getBalance(String[] t) throws RemoteException {
        if(t.length != 3) return "Sai cu phap";
        Account account  = new Account(t[1],t[2]);
        Account account2=  server.getBalance(account);
        if(account2 == null) return "Tai khoan hoac pin sai";
        return account2.toString();
    }

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Client client = new Client(1234);
        client.startClient();
    }
}
