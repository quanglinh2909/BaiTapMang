package DeThiLan1_2008;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OneConnection extends Thread {
    Socket socket;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    UserDAO userDAO;
    User user = null;
    boolean isLogin = false;


    public OneConnection(Socket socket) throws IOException {
        this.socket = socket;
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        userDAO = new UserDAO();
    }

    @Override
    public void run() {
        String request, response;
        printWriter.println("Welcome to NLU e-Bank");
        try {
            while (true) {
                request = bufferedReader.readLine();
                if (request.equalsIgnoreCase("QUIT")) break;
                response = analysisRequest(request);
                printWriter.println(response);
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String analysisRequest(String request) throws IOException {
        String[] t = request.replaceAll(" +", " ").split(" ");
        if (t.length <= 0) return "Vui long nhap cau lenh";
        switch (t[0]) {
            case "TEN":
                return loginName(t);
            case "MATKHAU":
                return loginPass(t);
            case "GUI":
                return sendPrice(t);
            case "LAY":
                return getPrice(t);
            case "CHUYEN":
                return transferPrice(t);
            default:
                return "Sai cu phap";
        }
    }

    private String loginName(String[] t) throws IOException {
        if (t.length != 2) return "Sai cu phap";
        if (isLogin) return "ban da dang nhap";
        user = userDAO.loginName(t[1]);
        if (user == null) return "Ten dang nhap ko ton tai";
        return "Vui long nhap mat khau";

    }

    private String loginPass(String[] t) throws IOException {
        if (user == null) return "vui long nhap ten dang nhap";
        if (t.length != 2) return "Sai cu phap";
        if (isLogin) return "ban da dang nhap";
        if (!userDAO.loginPass(user, t[1])) return "Sai pass";
        isLogin = true;
        return "dang nhap thanh cong";
    }

    private String sendPrice(String[] t) throws IOException {
        if (!isLogin) return "vui long dang nhap";
        if (t.length != 2) return "Sai cu phap";
        if( Double.parseDouble(t[1]) <= 0) return "vui long nhap so lon hon 0";
        User user1 = userDAO.sendPrice(user.getNumberAccount(), Double.parseDouble(t[1]));
        if (user1 == null) return "loi!!";
        return "So tien hien tai la : " + user1.getPrice();
    }

    private String getPrice(String[] t) throws IOException {
        if (!isLogin) return "vui long dang nhap";
        if (t.length != 2) return "Sai cu phap";
        if( Double.parseDouble(t[1]) <= 0) return "vui long nhap so lon hon 0";
        if (!userDAO.getPrice(user.getNumberAccount(), Double.parseDouble(t[1]))) return "So tien ko du";
        return "rut tien thanh cong";
    }

    private String transferPrice(String[] t) throws IOException {
        if (!isLogin) return "vui long dang nhap";
        if (t.length != 3) return "Sai cu phap";
        if( Double.parseDouble(t[1]) <= 0) return "vui long nhap so lon hon 0";
        if (!userDAO.transferPrice(user.getNumberAccount(), t[1], Double.parseDouble(t[2]))) return "So tien ko du";
        return "chuyen tien thanh cong";
    }
}
