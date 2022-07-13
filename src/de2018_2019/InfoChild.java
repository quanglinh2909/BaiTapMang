package de2018_2019;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.rmi.RemoteException;

public class InfoChild implements Serializable {
    private String maSo;
    private String name;
    private String ngaySinh;
    private String noiCT;

    public InfoChild(String maSo, String name, String ngaySinh, String noiCT) {
        this.maSo = maSo;
        this.name = name;
        this.ngaySinh = ngaySinh;
        this.noiCT = noiCT;
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNoiCT() {
        return noiCT;
    }

    public void setNoiCT(String noiCT) {
        this.noiCT = noiCT;
    }

    @Override
    public String toString() {
        return "InfoChild{" +
                "maSo='" + maSo + '\'' +
                ", name='" + name + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", NoiCT='" + noiCT + '\'' +
                '}';
    }
    public boolean save(RandomAccessFile rd) throws RemoteException {
        try {
            rd.seek(rd.length());
            rd.writeUTF(maSo);
            rd.writeUTF(name);
            rd.writeUTF(ngaySinh);
            rd.writeUTF(noiCT);
            return true;
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }

    }

}
