package java8.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TestStreamDemo {
    List<Transaction> list;

    @Before
    public void setup()
    {
        list = Arrays.asList(
                new Transaction(3, Transaction.TYPE.GROCERY, "G3"),
                new Transaction(1, Transaction.TYPE.GROCERY, "G1"),
                new Transaction(2, Transaction.TYPE.OTHER, "O2"),
                new Transaction(4, Transaction.TYPE.OTHER, "O4"),
                new Transaction(5, Transaction.TYPE.GROCERY, "G5")
        );
    }

    @Test
    public void testStreamDemo() {
        List<Integer> ids = new StreamDemo().streamDemo(list, Transaction.TYPE.GROCERY);
        assertEquals(3, ids.size());
        assertEquals(5, ids.get(0).intValue());
        assertEquals(3, ids.get(1).intValue());
        assertEquals(1, ids.get(2).intValue());
    }

    @Test
    public void testStreamEfficiyDemo() {
        List<Integer> ids = new StreamDemo().streamEfficiyDemo(list, Transaction.TYPE.GROCERY, 1);
        assertEquals(1, ids.size());
    }

    @Test
    public void  testStreamAnyDemo() {
        Optional<Transaction> optional = new StreamDemo().streamAnyDemo((Collection)list, Transaction.TYPE.GROCERY);

        optional.ifPresent(o -> {
            System.out.println("First object returned: " + o);
        });

        Transaction t = optional.isPresent()
                ? optional.get()
                : new Transaction(-1, Transaction.TYPE.NONE, "Otherwise");
        System.out.println("Optional operation: " + t);
    }

    @Test
    public void testStreamIteratordemo() {
        List<Transaction> data = new StreamDemo().streamIteratordemo(new MySupplier(), 10);
        System.out.println("data generated: " + data);
    }

    class MySupplier implements Supplier<Transaction> {
        private Iterator<Transaction> iterator = list.iterator();

        @Override
        public Transaction get() {
            if (iterator.hasNext()) {
                return iterator.next();
            }
            return null;
        }
    }

    public static class MyIntegerSupplier implements Supplier<Integer> {
        private int n = 0;

        @Override
        public Integer get() {
            Integer rc = n;
            n += 10; // 0, 10, 20, ...
            return rc;
        }
    }

    @Test
    public void testInfiniteInteger() {
        Stream<Integer> si = StreamDemo.generate(new MyIntegerSupplier(), 10);

        final int[] cnt = {0};
        si.forEach(d -> {
            cnt[0]++;
            System.out.println("d=" + d);
        });
        assertEquals(10, cnt[0]);

        si = StreamDemo.generate(new MyIntegerSupplier(), 20);
        final int[] cnt2 = {0};
        si.forEach(id->cnt2[0]++);
        assertEquals(20, cnt2[0]);
    }

    public static class MyIntegerSupplierWithPredicate implements StreamDemo.SupplierWithPredicate<Integer> {
        private int n = 0;
        private int count;

        MyIntegerSupplierWithPredicate(int count) {
            this.count = count;
        }

        @Override
        public Integer get() {
            Integer rc = n;
            n += 10; // 0, 10, 20, ...
            count--;
            return rc;
        }

        @Override
        public boolean continueProcess() {
            return count > 0;
        }
    }

    @Test
    public void testInfiniteInteger2() {
        Stream<Integer> si = StreamDemo.generateWithPredicate(
                new MyIntegerSupplierWithPredicate(10));

        final int[] s = {0};
        si.forEach(d -> s[0]++);
        assertEquals(10, s[0]);

        s[0] = 0;
        si = StreamDemo.generateWithPredicate(
                new MyIntegerSupplierWithPredicate(100)
        );
        si.forEach(d -> s[0]++);
        assertEquals(100, s[0]);
    }
}
