package java8.stream;

public class Transaction {
    public enum TYPE {
        NONE,
        GROCERY,
        OTHER
    }

    int id;
    TYPE type;
    String value;

    public Transaction(int id, TYPE type, String value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public TYPE getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + getId() + " - " + getValue();
    }
}
