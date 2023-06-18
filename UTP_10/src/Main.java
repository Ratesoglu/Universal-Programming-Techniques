public class Main {
    public static void main(String[] args) {
        String dirName =
                System.getProperty("user.home") + "/CP1250dir";
        String resultFileName = "UTF8res.txt";
        Futil.processDir(dirName, resultFileName);
    }
}

