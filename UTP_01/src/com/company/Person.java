package com.company;

public class Person implements
        IAggregable<Person, Integer>,
        IDeeplyCloneable<Person>{
    private int age;

    public Person(int age) {
        this.age = age;
    }

    public Integer aggregate(Integer accum)
    {
        if(accum!=null)
            return age+accum;
        return age;
    }
    public Person makeDeepCopy() throws CloneNotSupportedException{

        Person cloned = (Person)super.clone();
        return cloned;
    }

}
