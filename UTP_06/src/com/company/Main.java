package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        // Definitions of 'flines', 'join', 'collectInts'
        // and 'sum' in the form of lambda expressions:
        // your definitions here
        // End of definitions

        Function <String, List<String>> flines = path -> {
            ArrayList<String> l = new ArrayList<String>();
            Scanner sc;
            try {
                sc = new Scanner(new FileReader(path));
                while(sc.hasNextLine())
                    l.add(sc.nextLine());
                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return l;
        };

        Function <List<String>, String> join = list -> {
            String fullStr = "";
          return list.stream()
                  .map(str -> fullStr+str+" ")
                  .collect(Collectors.joining());
        };

        Function<String, List<Integer>> collectInts = e -> {
            List<Integer> intList = new ArrayList<>();
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(e);
            while (m.find()){
                intList.add(Integer.valueOf(m.group()));
            }
            return intList;
        };

        Function<List<Integer>, Integer> sum =  list ->
         list.stream().reduce(0,Integer::sum);
        ;







        String fname = "SLamComb.txt";
        InputConverter<String> fileConv =
                new InputConverter<>(fname);
        List<String> lines = fileConv.convertBy(flines);
        String text = fileConv.convertBy(flines, join);
        List<Integer> ints =
                fileConv.convertBy(flines,join,collectInts);
        Integer sumints =
                fileConv.convertBy(flines, join,
                        collectInts, sum);

        System.out.println(lines);
        System.out.println(text);
        System.out.println(ints);
        System.out.println(sumints);

        List<String> arglist = Arrays.asList(args);
        InputConverter<List<String>> slistConv =
                new InputConverter<>(arglist);
        sumints = slistConv.convertBy(join,collectInts,sum);
        System.out.println(sumints);
    }
}

class InputConverter<T>{
     T t;

    public InputConverter(T t) {
        this.t = t;
    }
    @SuppressWarnings({"rawtypes","unchecked"})
    public <F>F convertBy(Function... func) {
        List<Object> _functionList = new ArrayList<>();
        for (int i = 0; i <func.length ; i++) {
            if (i!=0)
                _functionList.add(func[i].apply(_functionList.get(i-1)));
            else
                _functionList.add(func[i].apply(t));
        }
        return (F) _functionList.get(_functionList.size()-1);
    }

}

