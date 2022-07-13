package de2018_2019;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    IChild server;
    private static String sessionId = "";
    private static String sessionIdUpLoad = "";

    public Client(int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(port);
        server = (IChild) registry.lookup("child");
    }

    public void initClient() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String response, request;
        response = server.getBanner();
        System.out.println(response);
        while (true) {
            request = scanner.nextLine().trim();
            if (request.equalsIgnoreCase("quit")) break;
            response = phanTich(request);
            System.out.println(response);
        }
        server.closeFile(sessionId);
    }

    private String phanTich(String request) throws RemoteException {
        String[] t = request.split("\\|");
        if (t.length <= 0) return "Vui long nhap lenh";
        switch (t[0]) {
            case "REGISTER":
                return register(t);
            case "SEND_FOTO":
                return upload(t);
            case "VIEW_INFO":
                return viewInfo(t);
            default:
                return "Sai cu phap";
        }

    }

    private String viewInfo(String[] t) throws RemoteException {
        if(t.length != 2) return "Sai cu phap";
        if(sessionId.trim().isEmpty()) return "Vui long dang ky";
        if(server.ViewInfo(t[1],sessionId) != null)
        return server.ViewInfo(t[1],sessionId).toString();
        return "sai ms";
    }

    private String upload(String[] t) throws RemoteException {
        if(t.length !=2) return "Sai cu phap";
        if(sessionId.trim().isEmpty()) return "Vui long dang ky";
        try {
            File file = new File(t[1]);
            InputStream  inputStream = new FileInputStream(file);
            byte[] data = new byte[1024 * 100];
            sessionIdUpLoad = server.openFileUpload(file.getName());
            int count;
            while ((count = inputStream.read(data)) != -1) {
                byte result[] = new byte[count];
                System.arraycopy(data, 0, result, 0, count);
                server.sendPhoto(file.getName(), result, sessionIdUpLoad);
            }
            inputStream.close();
            server.closeFileUpLoad(sessionIdUpLoad);
            return "upload thanh cong";
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }

    }

    private String register(String[] t) throws RemoteException {
        if (t.length != 4) return "sai cu phap";
        if (!checkDate(t[2])) return "ngay sinh ko hop le";
        sessionId = server.openFile();
        InfoChild infoChild = new InfoChild(sessionId + "", t[1], t[2], t[3]);
        if (!server.register(infoChild, sessionId)) return "loi";
        return "Dang ky thanh cong";

    }

    private boolean checkDate(String data) {
        String[] arr = data.split("/");
        try {
            int day = Integer.parseInt(arr[0]);
            int month = Integer.parseInt(arr[1]);
            int year = Integer.parseInt(arr[2]);
//            System.out.println(day+","+ month+","+ year);
            if (month > 12 || month < 1 || day > 31 || year < 1 || day < 1 || 2018 - year > 6) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Client client = new Client(12345);
        client.initClient();
    }

}
