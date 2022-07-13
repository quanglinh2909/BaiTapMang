package de2018_2019;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChild extends Remote {
    public boolean register(InfoChild child,String sessionID) throws RemoteException;
    public String getBanner() throws RemoteException;
    public boolean sendPhoto(String fileName,byte[] arr,String secId) throws RemoteException;
    public InfoChild ViewInfo(String ms,String secId) throws  RemoteException;
    public String openFile() throws RemoteException;
    public String openFileUpload(String fileName) throws RemoteException;
    public void closeFile(String sessionID) throws RemoteException;
    public void closeFileUpLoad(String sessionID) throws RemoteException;
}
