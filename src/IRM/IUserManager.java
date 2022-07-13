package IRM;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IUserManager extends Remote {
    public Account getBalance(Account account) throws  RemoteException;
    public Account withdraw(Account account, int amount) throws RemoteException;
    public Account deposit(Account account, int amount) throws RemoteException;
    public Account transfer(Account fromAccount, Account toAccount,
                        int amount) throws RemoteException;
    public List<History> report(Account account) throws RemoteException;

}
