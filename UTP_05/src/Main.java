import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {

        System.out.println("*** method of(...)");
        String s = "Alice";
        Maybe<String> m1 = Maybe.of(s);
        System.out.println(m1);
        s = null;
        Maybe<String> m2 = Maybe.of(s);
        System.out.println(m2);

        System.out.println("*** method ifPresent(...)");
        Integer num = null;
        Maybe<Integer> m3 = Maybe.of(num);
        if (num != null) System.out.println(num);
        m3.ifPresent(n -> System.out.println(n));
        m3.ifPresent(System.out::println);

        System.out.println("*** method map(...)");
        Maybe<Integer> m4 = Maybe.of(10);
        m4.ifPresent(System.out::println);
        Maybe<Integer> m5 = m4.map( n -> n +10 );
        System.out.println(m5);

        System.out.println("*** method get(...)");
        System.out.println(m5.get());
        try {
            // m2 was empty!
            System.out.println(m2.get());
        } catch(Exception exc) {
            System.out.println(exc);
        }

        System.out.println("*** method orElse(...)");



        String res = Maybe.of(num).map(
                n -> "num is: " + n)
                .orElse("no value");
        System.out.println(res);

        System.out.println("*** method filter(...)");
        String txt = "Dog";
        String msg = "";

        if (txt != null && txt.length() > 0) {
            msg = txt;
        } else {
            msg = "Txt is null or empty";
        }

        msg = Maybe.of(txt)
                .filter(t -> t.length() > 0)
                .orElse("txt is null or empty");
        System.out.println(msg);
    }
}
class Maybe <T> {

    private T value;

    private Maybe (T value) {
        this.value = value;
    }

    public static <F> Maybe<F> of(F value) {
        return new Maybe<>(value);
    }

    public void ifPresent (Consumer consumer) {
        if (this.value != null)
            consumer.accept(value);
    }

    public <F> Maybe<F> map (Function<T, F> function) {
        return this.value != null ? new Maybe<>(function.apply(this.value)) : new Maybe<>(null);
    }

    public boolean isPresent () {
        return value != null;
    }

    public T orElse (T value) {
        return (this.value != null) ? this.value : value;
    }

    public T get () {
        if (value == null)
            throw new NoSuchElementException(" Empty Maybe ");
        return this.value;
    }

    public Maybe<T> filter (Predicate<T> predicate) {
        if (this.value == null) {
            return this;
        } else if (predicate.test(this.value)){
            return this;
        } else return Maybe.of(null);
    }

    @Override
    public String toString() {
        if(this.value == null)
            return "Maybe [ Empty ]";
        else
            return "Maybe  [ " + this.value + " ]";
    }
}
