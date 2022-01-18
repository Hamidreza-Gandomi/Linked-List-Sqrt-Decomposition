public class Node {

    protected int data;
    protected Node next, previous;


    /*** constructor ***/
    public Node() {
        this.next = null;
        this.previous = null;
    }

    public Node(int data) {
        this.data = data;
    }

    public Node(int data, Node next, Node previous) {
        this.data = data;
        this.next = next;
        this.previous = previous;
    }

}
