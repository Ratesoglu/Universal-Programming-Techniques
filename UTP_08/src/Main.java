import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String allWords ="D:\\Ders Belgeleri\\3rd semester\\UTP\\UTP_08\\english_words_WIN.txt";
        Anagrams an = new Anagrams(allWords);

        System.out.println("************************");
        Scanner scan = new Scanner(new File("D:\\Ders Belgeleri\\3rd semester\\UTP\\UTP_08\\english_words_WIN.txt"));
        while(scan.hasNext()) {

            System.out.println(an.getAnagramsFor(scan.next()));

        }
        scan.close();

    }
}
class Anagrams {
    private final List<String> wordList = new ArrayList<>();
    private final HashMap<String, List<String>> anagrams = new HashMap<>();

    private static String sortChars(String input)
    {
        char[] output = input.toCharArray();
        Arrays.sort(output);
        return new String(output);
    }

    public Anagrams(String source) {
        if (source.toLowerCase().startsWith("http://")) {
            try {
                URL path = new URL(source);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(path.openStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null){
                    wordList.add(inputLine);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (Stream<String> stream = Files.lines(Paths.get(source))) {
                stream.forEach(l -> wordList.addAll(Arrays.asList(l.split("\\s+"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (String word : wordList) {
            String sorted = sortChars(word);

            if (anagrams.containsKey(sorted)) {
                anagrams.get(sorted).add(word);
            } else {
                List<String> list = new ArrayList<>();
                list.add(word);
                anagrams.put(sorted, list);
            }
        }
    }

    String getAnagramsFor(String value) {
        String key = sortChars(value);
        String anagramsFlat= "";

        if (anagrams.containsKey(key)) {
            anagramsFlat = anagrams.get(key).stream().filter(a -> !a.equals(value)).collect(Collectors.joining(", "));
        }

        return value + ": [" + anagramsFlat + "]";
    }

    public Iterable<List<String>> getSortedByAnQty() {
        return anagrams.entrySet().stream()
                .sorted((o1, o2) -> {
                    int o1s = o1.getValue().size();
                    int o2s = o2.getValue().size();
                    return Integer.compare(o2s, o1s);
                })
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b) -> {throw new AssertionError();},
                        LinkedHashMap::new
                )).values();
    }

}
