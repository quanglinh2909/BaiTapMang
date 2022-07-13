package IRM;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private String acountNumber;
    private String pinCode;
    private Double totalMoney;

    public Account(int id, String acountNumber, String pinCode, Double totalMoney) {
        this.id = id;
        this.acountNumber = acountNumber;
        this.pinCode = pinCode;
        this.totalMoney = totalMoney;
    }

    public Account(String acountNumber) {
        this.acountNumber = acountNumber;
    }

    public Account(String acountNumber, String pinCode) {
        this.acountNumber = acountNumber;
        this.pinCode = pinCode;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcountNumber() {
        return acountNumber;
    }

    public void setAcountNumber(String acountNumber) {
        this.acountNumber = acountNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", acountNumber='" + acountNumber + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", totalMoney=" + totalMoney +
                '}';
    }
}
