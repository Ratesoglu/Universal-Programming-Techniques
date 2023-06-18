
import java.util.*;
import java.util.function.BiConsumer;


public class Main {

    public static void main(String[] args) {
        // for convenience only...
        BiConsumer<String, Collection<String>> pr =
                (k,v)->System.out.println(k + " = " + v);

        ProgLang pl ;
        try {
            pl = new ProgLang("SProgrammers.tsv");
        } catch (Exception exc) {
            System.out.println("No input file? " + exc);
            return;
        }

        System.out.println("@1 Map of languages:");
        pl.getLangsMap().forEach(pr);

        System.out.println("@2 Map of programmers:");
        pl.getProgsMap().forEach(pr);

        System.out.println("@3 Languages sorted by " +
                " number of programmers:");
        pl.getLangsMapSortedByNumOfProgs().forEach(pr);

        System.out.println("@4 Programmers sorted by " +
                "number of languages:");
        pl.getProgsMapSortedByNumOfLangs().forEach(pr);

        System.out.println("@5 Original map of languages " +
                "is not modified:");
        pl.getLangsMap().forEach(pr);

        System.out.println("@6 Original map of programmer" +
                "s is not modified:");
        pl.getProgsMap().forEach(pr);

        System.out.println("@7 Map or programmers knowing" +
                " more than 1 language:");
        pl.getProgsMapForNumOfLangsGreaterThan(1).forEach(pr);

        System.out.println("@8 Original map of programmer" +
                "s is not modified:");
        pl.getProgsMap().forEach(pr);
    }
}

