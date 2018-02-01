package java8.stream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// Stream can be viewed as pipe operations, and is classified by three stages:
// (1) creation:
//     (a) a collection with stream() operator. e.g., list.stream()
//     (b) Stream's builder: Stream.iterate(start, unitary operator (NOTE: Spliterators is used
//     (c) Stream.generator with Supplier
// (2) intermediate operations:
//     (a) filter, limit
//     (b) sorted, distinct
//     (c) peek
//     (d) map  // transform to another data structure
// (3) terminating operations
//     (a) forEach
//     (b) collect
//     (c) reduce
//     (d) findAny
// Conditionally terminate a stream is not available in Java 8 (but can be worked around using Spliterators)
public class StreamDemo {

    public List<Integer> streamDemo(final List<Transaction> list, final Transaction.TYPE type) {
        return list.stream()
                .filter(t -> t.getType() == type)
                .sorted(Comparator.comparing(Transaction::getValue).reversed())
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }

    public List<Integer> streamEfficiyDemo(List<Transaction> list, Transaction.TYPE type, int num) {
        return list.stream()
                .peek( d -> {System.out.println("peek data: " + d);})
                .filter(t -> {
                    System.out.println("filter: " + t.getId());
                    return t.getType() == type;
                })
                .sorted(Comparator.comparing(Transaction::getValue).reversed())
                .map(o -> {
                    // NOTE: only 'num' printted out => efficient operation as demonstrated by "limit()"
                    System.out.println("map: " + o.getId());
                    return o.getId();
                })
                .limit(num)
                .collect(Collectors.toList());
    }

    public Optional<Transaction> streamAnyDemo(Collection<Transaction> list, Transaction.TYPE type) {
        return list.stream()
                .filter(t -> {
                    System.out.println("streamAnyDemo: filter: " + t);
                    return t.getType() == type;
                })
                .findAny();
    }

    public List<Transaction> streamIteratordemo(Supplier<Transaction> supplier, int limit) {
        Stream<Integer> numbers = Stream.iterate(0, n->n+10);  // start from 0, increment by 10; this is an infinite number stream
        numbers.limit(5)
                .forEach(System.out::println);  // get the first 5

        return Stream.generate(supplier)
                .limit(limit)
                .filter(t -> t != null)
                .collect(Collectors.toList());
    }

    // see https://stackoverflow.com/questions/35226823/how-do-streams-stop
    static <T> Stream<T> generate(Supplier<T> s, long count) {
        return StreamSupport.stream(
                new Spliterators.AbstractSpliterator<T>(count, Spliterator.SIZED) {
                    long remaining = count;

                    public boolean tryAdvance(Consumer<? super T> action) {
                        if (remaining <= 0) return false;
                        remaining--;
                        action.accept(s.get());
                        return true;
                    }
                }, false);
    }

    public interface SupplierWithPredicate<T> extends Supplier<T> {
        boolean continueProcess();
    }

    static <T> Stream<T> generateWithPredicate(SupplierWithPredicate<T> s) {
        return StreamSupport.stream(
                new Spliterators.AbstractSpliterator<T>(Integer.MAX_VALUE, Spliterator.SIZED) {
                    public boolean tryAdvance(Consumer<? super T> action) {
                        if (!s.continueProcess()) return false;
                        action.accept(s.get());
                        return true;
                    }
                }, false);
    }
}
