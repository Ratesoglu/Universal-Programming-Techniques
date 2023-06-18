package com.company;

import java.util.List;

public class MyContainer <TElement extends IAggregable<TElement,TResult>& IDeeplyCloneable<TElement> , TResult>
        implements IContainer{

    List<TElement> list;


    public MyContainer(List<TElement> persons) {
        this.list = persons;
    }


    public TResult aggregateAll() {
       TResult  result=null;
      for (int i = 0;i<list.size();i++){
          result=list.get(i).aggregate(result);
      }
      return result;
    }

    @Override
    public IAggregable cloneElementAtIndex(int index) throws CloneNotSupportedException{
        TElement tElement = list.get(index);
        return tElement;
    }


}
