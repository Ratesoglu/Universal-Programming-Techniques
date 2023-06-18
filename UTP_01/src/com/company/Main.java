package com.company;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Person> persons = Arrays.asList(new Person(12),new Person(71),new Person(17));
        MyContainer<Person,Integer> myContPerson =new MyContainer<>(persons);
        System.out.println(myContPerson.aggregateAll());
        List<Text> texts = Arrays.asList(new Text("A"), new Text("B"),new Text("C"), new Text("D"));
        MyContainer<Text,String> myContText = new MyContainer<>(texts);
        System.out.println(myContText.aggregateAll());
        List<Car> cars= Arrays.asList(new Car("Hyundai"),new Car("Ford"));
        MyContainer<Car,String> myCars = new MyContainer<>(cars);
        System.out.println(myCars.aggregateAll());


    }
}
