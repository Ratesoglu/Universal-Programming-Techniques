package com.company;

public class Text implements
        IAggregable<Text,String>,IDeeplyCloneable<Text> {

    private String txt;

    public Text(String txt) {this.txt = txt;}

    public String aggregate(String accum){
        if(accum==null)
            return txt;
        return accum+txt;}
    @Override
    public Text makeDeepCopy() throws CloneNotSupportedException {

        Text cloned = (Text) super.clone();
        return cloned;
    }

}
