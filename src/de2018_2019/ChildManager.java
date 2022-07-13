package de2018_2019;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChildManager extends UnicastRemoteObject implements IChild {
    private static long sessionId = 0;
    Map<String, RandomAccessFile> sessionMap = new HashMap<>();
    Map<String, FileOutputStream> sessionMapUpload = new HashMap<>();
    protected ChildManager() throws RemoteException {
        super();
    }


    @Override
    public boolean register(InfoChild child,String sessionID) throws RemoteException {
        return  child.save(sessionMap.get(sessionID));
    }


    @Override
    public String getBanner() throws RemoteException {
        return "Hello may";
    }
    @Override
    public String openFileUpload(String fileName) throws RemoteException {
        try {
            sessionId += 1;
            String t[] = fileName.split(".");
            File file = new File("src/data/MS"+sessionId+fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            sessionMapUpload.put("MS"+sessionId,fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RemoteException(e.getMessage());
        }

        return "MS"+sessionId;
    }
    @Override
    public boolean sendPhoto(String fileName,byte[] arr,String secId) throws RemoteException {
        try {
            FileOutputStream fileOutputStream = sessionMapUpload.get(secId);
            fileOutputStream.write(arr);
            return true;
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public InfoChild ViewInfo(String ms,String secId) throws RemoteException {
        try {
            RandomAccessFile randomAccessFile = sessionMap.get(secId);
            randomAccessFile.seek(0);
            InfoChild infoChild = null;
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()){
                String maSo = randomAccessFile.readUTF();
                 String name = randomAccessFile.readUTF();
                 String ngaySinh = randomAccessFile.readUTF();
                 String noiCT = randomAccessFile.readUTF();
                 if(maSo.equals(ms)){
                     infoChild = new InfoChild(maSo,name,ngaySinh,noiCT);
                     break;
                 }
            }
            return infoChild;
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }

    }

    @Override
    public String openFile() throws RemoteException {
        try {
            sessionId += 1;
            File file = new File("src/data/info.dat");
            RandomAccessFile rdf = new RandomAccessFile(file,"rw");
            sessionMap.put("MS"+sessionId,rdf);
        } catch (FileNotFoundException e) {
            throw new RemoteException(e.getMessage());
        }

        return "MS"+sessionId;
    }

    @Override
    public void closeFile(String sessionID) throws RemoteException {
        try {
            sessionMap.get(sessionID).close();
            sessionMap.remove(sessionID);
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    } @Override
    public void closeFileUpLoad(String sessionID) throws RemoteException {
        try {
            sessionMapUpload.get(sessionID).close();
            sessionMapUpload.remove(sessionID);
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    }


}
