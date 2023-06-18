package com.company;

public interface IContainer <TElement extends
        IAggregable<TElement,TResult>& IDeeplyCloneable<TElement>, TResult>{


    TResult aggregateAll();
    TElement cloneElementAtIndex(int index) throws CloneNotSupportedException;
}
