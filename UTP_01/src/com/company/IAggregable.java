package com.company;

public interface IAggregable<TElement extends IAggregable<TElement,TResult>,TResult> {
    TResult aggregate(TResult accum);
}

