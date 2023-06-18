import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        try {
            Path result = Paths.get(dirName + '/' + resultFileName);
            Files.walk(Paths.get(dirName))
                    .filter(e -> e.toString().endsWith(".txt")).forEach(e -> {
                try { Files.write(result, Files.readAllLines(e, Charset.forName("cp1250")), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND); }
                catch (IOException e1) { e1.printStackTrace(); }
            });
        } catch (IOException e) { e.printStackTrace(); }
    }
}
