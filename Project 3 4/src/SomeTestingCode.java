public class SomeTestingCode {
    public static void main(String[] args) {
        Node node = new Node(new Game(), null);
        node.setNext(new Node(new Consol3(), null));
        node.setData(new Consol3());
    }
}
