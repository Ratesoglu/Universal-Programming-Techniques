package com.company;

public interface IDeeplyCloneable<TElement extends IDeeplyCloneable<TElement>> {
    TElement makeDeepCopy() throws CloneNotSupportedException;

}
