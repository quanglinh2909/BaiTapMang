package DeThiLan1_2008;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    List<User> userList = new ArrayList<>();

    public List<User> getUserList() throws IOException {
        File file = new File("src\\user.txt");
        userList.clear();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] t = line.split("\t");
            User user = new User(t[0], t[1], t[2], Double.parseDouble(t[3]));
            userList.add(user);
        }
        return userList;
    }

    public User loginName(String name) throws IOException {
        for (User user : getUserList()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public boolean loginPass(User user, String pass) throws IOException {
        if (user.getPass().equals(pass)) {
            return true;
        }
        return false;
    }

    public User sendPrice(String numberAccount, double price) throws IOException {
        User userResult = null;
        List<User> list = getUserList();
        for (User user : list) {
            if (user.getNumberAccount().equals(numberAccount)) {
                user.setPrice(user.getPrice() + price);
                userResult = user;
                break;
            }
        }
        writeListUser(list);
        return userResult;
    }

    public boolean getPrice(String numberAccount, double price) throws IOException {
        boolean check = false;
        List<User> list = getUserList();
        for (User user : list) {
            if (user.getNumberAccount().equals(numberAccount) && user.getPrice() >= price) {
                user.setPrice(user.getPrice() - price);
                check = true;
                break;
            }
        }
        if (check)
            writeListUser(list);
        return check;
    }

    public boolean transferPrice(String moveNumberAccount, String takeNumberAccount, double price) throws IOException {
        boolean check = false;
        List<User> list = getUserList();
        int indexUserTake = 0;

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getNumberAccount().equals(moveNumberAccount) && user.getPrice() >= price && checkNumberAccount(takeNumberAccount)) {
                check = true;
                user.setPrice(user.getPrice() - price);
            }
            if (user.getNumberAccount().equals(takeNumberAccount)) {
                indexUserTake = i;
            }
        }
        if (check) {
            User user = list.get(indexUserTake);
            user.setPrice(user.getPrice() + price);
            writeListUser(list);

        }
        return check;
    }

    public boolean checkNumberAccount(String moveNumberAccount) throws IOException {
        for (User user : userList) {
            if (user.getNumberAccount().equals(moveNumberAccount)) {
                return true;
            }
        }
        return false;
    }


    public void writeListUser(List<User> userList) throws IOException {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("src\\user.txt")));
        for (User user : userList ) {
            String res = user.getName() + "\t" + user.getPass() + "\t" + user.getNumberAccount() + "\t" + user.getPrice()+"\n";
            printWriter.write(res);
        }
        printWriter.flush();

    }

    public static void main(String[] args) throws IOException {
        UserDAO userDAO = new UserDAO();
        System.out.println(userDAO.transferPrice("kcntt","kcntt222",100));
    }
}
