import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Integer[] ints = {88, 99};
        Set<Integer> set =
                new HashSet<>(Arrays.asList(4, 7));

        System.out.println("*** Creating XLists");
        XList<Integer> list1 = new XList<>(1, 3, 9);
        XList<Integer> list2 = XList.of(5, 6);
        XList<Integer> list3 = new XList<>(ints);
        XList<Integer> list4 = XList.of(ints);
        XList<Integer> list5 = new XList<>(set);
        XList<Integer> list6 = XList.of(set);
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(list4);
        System.out.println(list5);
        System.out.println(list6);

        System.out.println("*** Special funcs for Strings");
        XList<String> slist1 = XList.charsOf("ab cd efg");
        XList<String> slist2 = XList.tokensOf("ab cd efg");
        XList<String> slist3 = XList.tokensOf("A-B-C", "-");
        System.out.println(slist1);
        System.out.println(slist2);
        System.out.println(slist3);

        System.out.println("*** Union");
        List<Integer> m1 = list1.union(list2);
        System.out.println(m1);
        m1.add(11);
        System.out.println(m1);
        XList<Integer> m2 = (XList<Integer>) m1;
        XList<Integer> m3 =
                m2.union(ints).union(XList.of(4, 4));
        System.out.println(m2);
        System.out.println(m3);
        m3 = m3.union(set);
        System.out.println(m3);

        System.out.println("*** Diff");
        System.out.println(m3.diff(set));
        System.out.println(XList.of(set).diff(m3));

        System.out.println("*** Unique");
        XList<Integer> uniq = m3.unique();
        System.out.println(uniq);

        System.out.println("*** Combinations");
        List<String> sa = Arrays.asList("a", "b");
        List<String> sb = Arrays.asList("X");
        XList<String> sc = XList.charsOf("12");
        XList toCombine = XList.of(sa, sb, sc);
        XList<XList<String>> cres = toCombine.combine();
        System.out.println(cres);


        System.out.println("*** Collect and join");
        XList<String> j1 = cres.collect(li -> li.join());
        System.out.println(j1.join(" "));
        XList<String> j2 =cres.collect(li -> li.join("-"));
        System.out.println(j2.join(" "));

        System.out.println("*** ForEachWithIndex");
        XList<Integer> lmod =
                XList.of(1,2,8, 10, 11, 30, 3, 4);
        lmod.forEachWithIndex( (e, i) -> lmod.set(i, e*2));
        System.out.println(lmod);
        lmod.forEachWithIndex( (e, i) -> {
            if (i % 2 == 0) lmod.remove(e);
        });
        System.out.println(lmod);
        lmod.forEachWithIndex( (e, i) -> {
            if (i % 2 == 0) lmod.remove(i);
        });
        System.out.println(lmod);
    }
}
 class XList<T> extends ArrayList<T>{

     public List<XList> list ;
     public XList(Collection<T> coll){
        super(coll);
    }
     public XList(T... args){
        super(Arrays.asList(args));
     }
     public static <T> XList<T> of(Collection<T> coll){
        return new XList<T>(coll);
     }
     public static <T> XList<T> of(T... args){
        return new XList<T>(Arrays.asList(args));
     }
     public static XList<String> charsOf(String str){
        List<String> returnList =new ArrayList<>();
         for (int i = 0; i <str.length() ; i++) {
             returnList.add(String.valueOf(str.charAt(i)));
         }
        return new XList<String>(returnList);
     }
     public static XList<String> tokensOf(String str, String sep){
         return XList.of(str.split(sep));
     }
     public static XList<String> tokensOf(String str){
         return XList.tokensOf(str, "\\s+");
     }
     public XList<T> union(Collection<? extends T> coll){
        XList<T> list = new XList<T>(this);
        list.addAll(coll);
        return list;
     }
     public XList<T> union(T... arr){
        XList<T> list = new XList<>(this);
        list.addAll(Arrays.asList(arr));
        return list;
     }
     public XList<T> diff(Collection<? extends T> col){
         XList<T> list = new XList<>(this);
         list.removeAll(col);
         return list;
     }
     public XList<T> diff(T... arr){
        XList<T> list = new XList<>(this);
        list.removeAll(Arrays.asList(arr));
        return list;
     }
     public XList<T> unique(){
        return new XList<>(new LinkedHashSet<>(this));
     }
     public XList<XList<T>> combine(){
         XList<XList<T>> combines = new XList<>();
         ArrayList<List> tmp = (ArrayList<List>) this;

         int size = 1;
         for (int i = 0; i < this.size(); i++){
             size = size * tmp.get(i).size();
         }
         for (int i = 0; i < size; i++){
             combines.add(new XList<>());
         }

         int semiSize = 1;
         for (List aTmp : tmp) {
             int position = 0;
             for (int i = 0, j = 0; i < size; i++, j++) {
                 if (j == semiSize) {
                     position++;
                     j = 0;
                 }

                 if (aTmp.size() <= position)
                     position = 0;

                 ((ArrayList) combines.get(i)).add(aTmp.get(position));
             }
             semiSize = semiSize * aTmp.size();
         }

         return combines;
     }
     public <R> XList<R> collect(Function<? super T, ? extends R> fun){
         List<R> returnList = new XList<R>();
         for (T t: this) {
             returnList.add(fun.apply(t));
         }
         return new XList<R>(returnList);
     }
     public String join(String sep){
         return new XList<>(this)
                 .stream()
                 .map(Object::toString)
                 .collect(Collectors.joining(sep));
     }
     public String join(){
         return new XList<>(this)
                 .stream()
                 .map(Object::toString)
                 .collect(Collectors.joining(""));
     }
     public void forEachWithIndex(BiConsumer<? super T, Integer> cons){
         for (int i = 0; i < this.size(); i++) {
             cons.accept(this.get(i), i);
         }
     }


 }
