package com.GuiLesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public Main() {
        List<Integer> list1 =
                Arrays.asList(1, 5, 9, 13, 17);
        System.out.println(test1(list1));

        List<String> list2 =
                Arrays.asList("wxyz", "ijk", "abcde" );
        System.out.println(test2(list2));
    }

    public List<Integer> test1(List<Integer> src) {

        Selector<Integer> sel = new Selector<Integer>() {
            @Override
            public boolean select(Integer integer) {
                return integer < 10;
            }
        };

        Mapper<Integer,Integer> map = new Mapper<Integer, Integer>() {
            @Override
            public Integer map(Integer integer) {
                return integer + 10;
            }
        };

        return ListCreator.collectFrom(src)
                .when(sel).mapEvery(map);
    }

    public List<Integer> test2(List<String> src) {
        Selector<String> sel = new Selector<String>() {
            @Override
            public boolean select(String s) {
                return s.length() > 3;
            }
        };

        Mapper<String,Integer> map = new Mapper<String, Integer>() {
            @Override
            public Integer map(String s) {
                return s.length() + 10;
            }
        };

        return ListCreator
                .collectFrom(src)
                .when(sel)
                .mapEvery(map);
    }

    public static void main(String[] args) {
        new Main();

    }
    }
 class ListCreator<T>  {
    List<T> list;

    public ListCreator(List<T> list){

        this.list = list;
    }


    public static<T> ListCreator<T> collectFrom(List<T> src) {
        return new ListCreator<>(src);
    }

    public ListCreator<T> when(Selector<T> select){

        List<T> newL = new ArrayList<T>();

        for(T t : list){
            if (select.select(t))
                newL.add(t);
        }

        list = newL;
        return this;


    }

    public<E> List<E> mapEvery(Mapper<T,E> mapper){


        List<E> newL = new ArrayList<E>();

        for(T t : list){
            newL.add(mapper.map(t));
        }

        return newL;
    }
}
interface Selector <Control>  {
    boolean select(Control control);
}
interface Mapper <T , E> {
    E map(T t);
}


