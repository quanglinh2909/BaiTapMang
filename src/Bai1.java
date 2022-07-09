import java.io.File;
import java.util.List;

public class Bai1 {

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) return true;
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) for (File f : listFiles)
                deleteFile(f.getAbsolutePath());
        }
        return file.delete();
    }

    public static void findAll(String path, String... txt) {
        File file = new File(path);
        if (!file.exists()) return;
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) for (File f : listFiles) {
                findAll(f.getAbsolutePath(), txt);
            }
        }
        if (file.isFile() && checkPath(file.getAbsolutePath(), txt)) System.out.println(file.getAbsolutePath());


    }

    private static boolean checkPath(String path, String... txt) {
//        System.out.println(path);
        for (String t : txt)
            if (path.endsWith(t)) return true;
        return false;
    }

    public static void main(String[] args) {
//        System.out.println(deleteFile("D:\\t"));
        findAll("D:\\t",".txt");
    }
}
