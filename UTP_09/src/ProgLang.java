import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang {
    private ArrayList<String> lines;


    public ProgLang(String fileName) {
       this.lines=new ArrayList<>();
       try {
           BufferedReader reader = new BufferedReader(new FileReader(fileName));
           String line;
                while ((line = reader.readLine())!=null){
               lines.add(line);
           }
           reader.close();
       }catch (IOException exception){
           exception.printStackTrace();
       }
    }

    private void add(LinkedHashMap<String,LinkedHashSet<String>> progsMap,String key,String value){
        LinkedHashSet<String> variable = progsMap.get(key);
        if (variable == null) {
            variable = new LinkedHashSet<String>();
            progsMap.put(key,variable);

        }
        variable.add(value);
    }
    public Map<String,LinkedHashSet<String>> getLangsMap(){
        LinkedHashMap<String, LinkedHashSet<String>> langsMap = new LinkedHashMap<String, LinkedHashSet<String>>();
        for (String line: this.lines) {
            String[] seperatedLines = line.split("\t");
            String lang = "";

            for (int i = 0; i <seperatedLines.length ; i++) {
                String element = seperatedLines[i];
                if (i == 0) {
                    lang = element;
                    continue;
                }
                add(langsMap,lang,element);
            }
        }
        return langsMap;
    }
    public Map<String, LinkedHashSet<String>> getProgsMap(){
        LinkedHashMap<String, LinkedHashSet<String>> programmersMap = new LinkedHashMap<String, LinkedHashSet<String>>();

        for (String line : this.lines) {
            String[] splittedLine = line.split("\t");
            String lang = "";

            for (int i = 0; i < splittedLine.length; i++) {
                String element = splittedLine[i];

                if (i == 0) {
                    lang = element;
                    continue;
                }

                this.add(programmersMap, element, lang);
            }
        }

        return programmersMap;
    }
    public Map<String, LinkedHashSet<String>> getLangsMapSortedByNumOfProgs() {
        return this.sorted(
                this.getLangsMap(),
                (e1, e2) -> {
                    int diff = e2.getValue().size() - e1.getValue().size();

                    if (diff == 0) {
                        return e1.getKey().compareTo(e2.getKey());
                    }

                    return diff;
                }
        );
    }
    public Map<String, LinkedHashSet<String>> getProgsMapSortedByNumOfLangs() {
        return this.sorted(
                this.getProgsMap(),
                (e1, e2) -> {
                    int diff = e2.getValue().size() - e1.getValue().size();

                    if (diff == 0) {
                        return e1.getKey().compareTo(e2.getKey());
                    }

                    return diff;
                }
        );
    }
    public Map<String, LinkedHashSet<String>> getProgsMapForNumOfLangsGreaterThan(int i) {
        return this.filtered(
                this.getProgsMap(),
                (value) -> value.getValue().size() > i
        );
    }


    public  <T, U> Map<T,U> sorted(Map<T,U> map, Comparator<Map.Entry<T,U>> comparator) {
        return map
                .entrySet()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    public  <T, U> Map<T, U> filtered(Map<T, U> map, Predicate<Map.Entry<T,U>> predicate) {
        return map
                .entrySet()
                .stream()
                .filter(predicate)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}
