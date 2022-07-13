package IRM;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager extends UnicastRemoteObject implements IUserManager {
    protected UserManager() throws RemoteException {
        super();
    }

    @Override
    public Account getBalance(Account account) throws RemoteException {
        Connection connection = ConnectionDatabase.getInstance().getConnection();
        String sql = "select * from Account where acountNumber = ? and pinCode = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getAcountNumber());
            preparedStatement.setString(2, account.getPinCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            Account account1 = null;
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String accountNumber = resultSet.getString("acountNumber");
                String pinCode = resultSet.getString("pinCode");
                double totalMoney = resultSet.getDouble("totalMoney");
                account1 = new Account(id, accountNumber, pinCode, totalMoney);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return account1;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }

    }

    @Override
    public Account withdraw(Account account, int amount) throws RemoteException {
        Connection connection = ConnectionDatabase.getInstance().getConnection();
        String sql = "select * from Account where acountNumber = ? and pinCode = ? ";
        try {
            PreparedStatement pre = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pre.setString(1, account.getAcountNumber());
            pre.setString(2, account.getPinCode());
            ResultSet resultSet = pre.executeQuery();
            Account account1 = null;
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String accountNumber = resultSet.getString("acountNumber");
                String pinCode = resultSet.getString("pinCode");
                double totalMoney = resultSet.getDouble("totalMoney");
                if (totalMoney - amount >= 0 && amount > 0) {
                    resultSet.updateDouble("totalMoney", totalMoney - amount);
                    resultSet.updateRow();
                    account1 = new Account(id, accountNumber, pinCode, totalMoney - amount);
                }

            }
            resultSet.close();
            pre.close();
            connection.close();
            return account1;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public Account deposit(Account account, int amount) throws RemoteException {
        Connection connection = ConnectionDatabase.getInstance().getConnection();
        String sql = "select * from Account where acountNumber = ? and pinCode = ? ";
        try {
            PreparedStatement pre = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pre.setString(1, account.getAcountNumber());
            pre.setString(2, account.getPinCode());
            ResultSet resultSet = pre.executeQuery();
            Account account1 = null;
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String accountNumber = resultSet.getString("acountNumber");
                String pinCode = resultSet.getString("pinCode");
                double totalMoney = resultSet.getDouble("totalMoney");
                resultSet.updateDouble("totalMoney", totalMoney + amount);
                resultSet.updateRow();
                account1 = new Account(id, accountNumber, pinCode, totalMoney + amount);


            }
            resultSet.close();
            pre.close();
            connection.close();
            return account1;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    public Account deposit2(Account account, int amount) throws RemoteException {
        Connection connection = ConnectionDatabase.getInstance().getConnection();
        String sql = "select * from Account where acountNumber = ?  ";
        try {
            PreparedStatement pre = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pre.setString(1, account.getAcountNumber());
            ResultSet resultSet = pre.executeQuery();
            Account account1 = null;
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String accountNumber = resultSet.getString("acountNumber");
                String pinCode = resultSet.getString("pinCode");
                double totalMoney = resultSet.getDouble("totalMoney");
                resultSet.updateDouble("totalMoney", totalMoney + amount);
                resultSet.updateRow();
                account1 = new Account(id, accountNumber, pinCode, totalMoney + amount);


            }
            resultSet.close();
            pre.close();
            connection.close();
            return account1;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public Account transfer(Account fromAccount, Account toAccount, int amount) throws RemoteException {
        Connection connection = ConnectionDatabase.getInstance().getConnection();
        String sql = "select * from Account where acountNumber = ? and pinCode = ? ";
        String sql2 = "select * from Account where acountNumber = ? ";
        String sql3 = "update Account set totalMoney = ?  where acountNumber = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pre.setString(1, fromAccount.getAcountNumber());
            pre.setString(2, fromAccount.getPinCode());
            ResultSet resultSet = pre.executeQuery();

            Account account1 = null;
            if (resultSet.next() ) {
                int id = resultSet.getInt("ID");
                String accountNumber = resultSet.getString("acountNumber");
                String pinCode = resultSet.getString("pinCode");
                double totalMoney = resultSet.getDouble("totalMoney");
                if (totalMoney - amount >= 0 && amount > 0) {
                    PreparedStatement pre2 = connection.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    pre2.setString(1, toAccount.getAcountNumber());
                    ResultSet resultSet2 = pre2.executeQuery();
                    boolean check =false;
                    if (resultSet2.next()) {
                        double totalMoney2 = resultSet2.getDouble("totalMoney");
                        PreparedStatement pre3 = connection.prepareStatement(sql3, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pre3.setString(1, toAccount.getAcountNumber());
                        pre3.setDouble(2,totalMoney2 + amount);
                        check =true;
                    }
                    if(check){
                        resultSet.updateDouble("totalMoney", totalMoney - amount);
                        resultSet.updateRow();
                        account1 = new Account(id, accountNumber, pinCode, totalMoney - amount);

                    }

                }

            }
            resultSet.close();
            pre.close();
            connection.close();
            return account1;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public List<History> report(Account account) throws RemoteException {
        Connection connection = ConnectionDatabase.getInstance().getConnection();
        String sql = "select * from history where IdAccount = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pre.setInt(1, account.getId());
            ResultSet resultSet = pre.executeQuery();
            List<History> result = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                Date date = resultSet.getDate("date");
                String action = resultSet.getString("action");
                int quantity = resultSet.getInt("quantity");
                int total = resultSet.getInt("total");
                int IdAccount = resultSet.getInt("IdAccount");
                History history = new History(id, date, action, quantity, total, IdAccount);
                result.add(history);


            }
            resultSet.close();
            pre.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    public static void main(String[] args) throws RemoteException {
        UserManager userManager = new UserManager();
        System.out.println(userManager.transfer(new Account("1", "1"), new Account("2"), 100));
    }

}
