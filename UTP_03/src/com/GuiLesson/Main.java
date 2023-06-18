package com.GuiLesson;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //This is an application that does find flights from WAW to another countries
        //then converts Money EUR to PLN
        List<String> dest = Arrays.asList(
                "LAA   WAW   2000",
                "WAW  HAV   1200",
                "PRG  HAV  789",
                "WAW  DPS  2000",
                "WAW  HKT  1000"
        );
        double rate = 4.30;
        List<String> result = dest
                .stream()
                .filter(city -> city.startsWith("WAW"))
                .map(d -> {
                    String[] data = d.split("\\s+");
                    return String.format("to %s - price in PLN: %s", data[1], Double.parseDouble(data[2]) * rate);
                })
                .collect(Collectors.toList());
        result.stream().forEach(System.out::println);

    }
}
  //  git push -u origin master
