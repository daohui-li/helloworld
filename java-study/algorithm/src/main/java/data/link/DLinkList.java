package data.link;

public class DLinkList<V> {
    private V v;
    private DLinkList<V> prev;
    private DLinkList<V> next;

    public DLinkList(V v) {
        this(v, null, null);
    }

    public DLinkList(V v, DLinkList<V> prev, DLinkList<V> next) {
        this.v = v;
        this.prev = prev;
        this.next = next;
    }

    public void addNext(V v) {
        DLinkList<V> tmp = this.next;
        this.next = new DLinkList<V>(v, this, tmp);
    }

    public void addBefore(V v) {
        DLinkList<V> tmp = this.prev;
        this.prev = new DLinkList<V>(v, tmp, this);
    }

    public static <X> boolean remove(DLinkList<X> list, X x) {
        boolean deleted = false;
        DLinkList<X> current = list;
        while (current != null && !deleted) {
            deleted = removeCurrent(current, x);
            current = current.prev;
        }
        if (!deleted) {
            current = list.next;
            while (current != null && !deleted) {
                deleted = removeCurrent(current, x);
                current = current.next;
            }
        }
        return deleted;
    }

    private static <X> boolean removeCurrent(DLinkList<X> list, X x) {
        boolean deleted = false;
        if (x.equals(list.v)) {
            list.v = list.next.v;
            list.next.prev = list.prev;
            deleted = true;
        }
        return deleted;
    }
}
