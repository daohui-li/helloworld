package data.link;

public class LinkList<V> {
    private V value;
    private LinkList<V> next;

    public LinkList(V v) {
        this(v, null);
    }

    public LinkList(V value, LinkList<V> next) {
        this.value = value;
        this.next = next;
    }

    public LinkList<V> addNext(V v) {
        this.next = new LinkList<>(v, this.next);
        return this.next;
    }

    public LinkList<V> addBefore(V v) {
        return new LinkList<V>(v, this);
    }

    public LinkList<V> remove(V v) {
        if (this.value.equals(v)) {
            return this.next;
        }
        this.next = this.next.remove(v);
        return this;
    }

    public LinkList<V> find(V v) {
        LinkList<V> curr = this;
        while (curr != null) {
            if (v.equals(curr.value)) {
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    @Override
    protected LinkList<V> clone() throws CloneNotSupportedException {
        LinkList<V> clonedNext = null;
        if (this.next != null) {
            clonedNext = this.next.clone();
        }
        // TODO: how to clone value? see discussion in https://books.google.com/books?id=ka2VUBqHiWkC&pg=PA55&lpg=PA55&dq=effective+java+clone&source=bl&ots=yXGhLnv4O4&sig=zvEip5tp5KGgwqO1sCWgtGyJ1Ns&hl=en&ei=CYANSqygK8jktgfM-JGcCA&sa=X&oi=book_result&ct=result#v=onepage&q=effective%20java%20clone&f=false
        return new LinkList<V>(this.value, clonedNext);
    }

    @Override
    public String toString() {
        String rc = value.toString();
        if (this.next != null) {
            rc = rc + " => [" + this.next.toString() + "]";
        }
        return rc;
    }
}
