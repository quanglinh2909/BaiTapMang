import java.io.*;

public class BaiTapIO {
    public static boolean coppy(String sFile, String destFile, boolean moved) throws IOException {
        File file = new File(sFile);
        if(!file.exists() || !file.isFile()) return false;
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(destFile);
        int count;
        byte[] data = new byte[2048*100];

        while ((count = fileInputStream.read(data)) != -1){
            fileOutputStream.write(data,0,count);
        }
        if(moved) file.delete();
        return true;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(coppy("D:\\t\\New folder\\New folder\\d.txt","D:\\t\\New folder\\New folder\\h.txt",true
        ));
    }

}
