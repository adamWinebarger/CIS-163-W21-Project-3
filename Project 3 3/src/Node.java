import java.io.Serializable;

public class Node implements Serializable {
    private Rental data;
    private Node next;

    public Node(Rental data, Node next) {
        super();
        this.data = data;
        this.next = next;
    }

    public Node() {
    }

    public Rental getData() {
        return data;
    }

    public void setData(Rental data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }



}