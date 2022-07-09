package DeThiLan1_2008;

public class User {
    private String name;
    private String pass;
    private String numberAccount;
    private double price;

    public User(String name, String pass, String numberAccount, double price) {
        this.name = name;
        this.pass = pass;
        this.numberAccount = numberAccount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", numberAccount='" + numberAccount + '\'' +
                ", price=" + price +
                '}';
    }
}
