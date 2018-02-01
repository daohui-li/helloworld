package data.link;

import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class TestLinkList {
    @Test
    public void testClone() throws CloneNotSupportedException {
        LinkList<Integer> v = build(1, 2, 3, 0);
        System.out.println("original: " + v.toString());
        Stream<Integer> stream = Stream.generate(new Generator(1, 2, 3, 0));
        LinkList<Integer> clone = v.clone();
        System.out.println("cloned: " + clone.toString());
    }

    @Test
    public void testAdd() {
        LinkList<Integer> v = build(1, 3, 0);
        v.addNext(2);
        System.out.println(v.toString());
    }

    @Test
    public void testAddBefore() {
        LinkList<Integer> v = build(2, 3, 0);
        v = v.addBefore(1);
        System.out.println(v);
    }

    @Test
    public void testRemove() {
        LinkList<Integer> v = build(1, 2, 3, 4, 0);
        System.out.println("origin: " + v);

        v = v.remove(1);
        System.out.println("delete first element(1): " + v.toString());

        v = v.remove(0);
        System.out.println("delete last element(0): " + v.toString());

        v = v.remove(3);
        System.out.println("delete middle element(3): " + v.toString());
    }

    @Test
    public void testFind() {
        LinkList<Integer> v = build(1, 2, 3, 4, 0);
        System.out.println("origin: " + v);

        System.out.println("find 1: " + v.find(1));  // 1->2->3->4->0
        System.out.println("find 3: " + v.find(3));  // 3->4->0
        System.out.println("find 0: " + v.find(0));  // 0
        System.out.println("find 6: " + v.find(6));  // null
    }

    private class Generator implements Supplier<Integer> {
        Integer[] integers;
        int counter;

        public Generator(Integer ... integers) {
            this.integers = integers;
            counter = 0;
        }

        @Override
        public Integer get() {
            while (counter < integers.length) {
                return integers[counter++];
            }
            return null;
        }
    }

    private LinkList<Integer> build(Integer ... integers) {
        LinkList<Integer> rc = null;
        LinkList<Integer> current = rc;
        for (Integer integer: integers) {
            if (current != null) {
                current = current.addNext(integer);
            } else {
                current = new LinkList<Integer>(integer);
                rc = current;
            }
        }
        return rc;
    }
}
