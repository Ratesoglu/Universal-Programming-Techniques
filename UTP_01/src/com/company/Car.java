package com.company;

public class Car implements
        IAggregable<Car, String>,
        IDeeplyCloneable<Car> {
    String name;

    public Car(String name) {
        this.name = name;
    }

    @Override
    public String aggregate(String accum) {
       if (accum==null)
           return name;

       return accum+" "+name;
    }

    @Override
    public Car makeDeepCopy() throws CloneNotSupportedException {
        Car cloned= (Car) super.clone();
        return cloned;
    }
}
