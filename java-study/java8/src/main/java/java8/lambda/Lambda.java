package java8.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Lambda {
    public String use(String text, Foo func) {
        return func.display(text);
    }

    public void demo() {
        Foo foo = d -> d + " generated from Lambda";
        System.out.println(this.use("text", foo));

        // alternative approach and also demo for single argument
        Function<String, String> foo2 = d -> " also generated from Lambda";
        System.out.println(foo2.apply("text"));

        // void argument in lambda
        Runnable runner = () -> { System.out.println("hello world - from lambda");};
        runner.run();

        TwoArgs twoArgs = (x, y) -> x + " and " + y;
        System.out.println(twoArgs.method("foo", "bar"));

        // use function methods directly
        List<String> list = Arrays.asList("a1", "b2", "c3");
        list.forEach(this::func);

        list.forEach(Lambda::staticFunc);
    }

    private void func(String x) {
        System.out.println(x + " displayed by func");
    }

    static void staticFunc(String x) {
        System.out.println(x + " displayed by staticFunc");
    }

    @FunctionalInterface
    private interface TwoArgs {
        String method(String s1, String s2);
    }
}
