package IRM;

import java.sql.Date;

public class History {
    private String id;
    private Date date;
    private String action;
    private int quantity;
    private int total;
    private int idAccount;

    public History(String id, Date date, String action, int quantity, int total, int idAccount) {
        this.id = id;
        this.date = date;
        this.action = action;
        this.quantity = quantity;
        this.total = total;
        this.idAccount = idAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", action='" + action + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", idAccount=" + idAccount +
                '}';
    }
}
