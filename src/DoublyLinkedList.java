public class DoublyLinkedList {

    protected Node head;
    protected Node tail;
    protected int len;

    /*** constructor ***/
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.len = 0;
    }

    // use to checks the list is empty or not
    public boolean isEmpty() {
        return head == null;
    }

    // used to insert a node
    public void insert(int data) {
        insertAtLast(data);
    }

    // used to insert a node at the start
    public void insertAtFirst(int data) {
        Node new_Node = new Node(data, head, null);
        if (!isEmpty())
            head.previous = new_Node;
        head = new_Node;
        if (tail == null)
            tail = new_Node;
        len++;
    }

    // used to insert a node at the end
    public void insertAtLast(int data) {
        Node new_Node = new Node(data, null, tail);
        if (tail != null)
            tail.next = new_Node;
        tail = new_Node;
        if (isEmpty())
            head = new_Node;
        len++;
    }

    // used to insert a new node at a given position
    void insertAt(int data, int position) {

        Node new_Node = new Node(data, null, null);

        // check if the position is > 0
        if (position < 1) {
            System.out.print("\nposition should be >= 1.");
        } else if (position == 1) {
            insertAtFirst(data);
        } else {

            Node temp = new Node();
            temp = head;

            for (int i = 1; i < position - 1; i++) {
                if (temp != null) {
                    temp = temp.next;
                }
            }

            // If the previous node is not null, adjust the links
            if (temp != null) {
                new_Node.next = temp.next;
                new_Node.previous = temp;
                temp.next = new_Node;
                if (new_Node.next != null)
                    new_Node.next.previous = new_Node;
            } else {
                // When the previous node is null
                System.out.print("\nThe previous node is null.");
                return;
            }
            len++;
        }
    }


    // used to delete a node
    public void deleteNode(Node del) {

        // if list is NULL or Node is NULL
        if (isEmpty() || del == null) {
            return;
        }

        // if node to be deleted is head node
        if (head == del) {
            head = del.next;
        }

        // change next only if node to be deleted is not the last node
        if (del.next != null) {
            del.next.previous = del.previous;
        }

        // change previous only if node to be deleted is not the first node
        if (del.previous != null) {
            del.previous.next = del.next;
        }
        len--;
    }

    // used to delete a node at a given position
    public void deleteAt(int position) {

        // if list is NULL or invalid position is given
        if (isEmpty() || position <= 0)
            return;

        Node current = head;

        // move up to the specified node at position from the beginning
        for (int i = 1; current != null && i < position; i++) {
            current = current.next;
        }

        // if position > number of nodes
        if (current == null)
            return;

        // delete the node 'current'
        deleteNode(current);
    }

    // used to delete a node from the end of the list
    public void deleteEnd() {

        // if list in NULL or not
        if (isEmpty()) {
            return;
        } else {
            // continue until the list has one node
            if (head != tail) {
                tail = tail.previous;
                tail.next = null;
            }
            // if the list contains only one element Then it will remove node and now both head and tail will point to null
            else {
                head = tail = null;
            }
        }
        len--;
    }

    // used to get data from a specified index
    public int getData(int index) {

        Node current = head;
        int counter = 1; // index of the first Node
        while (current != null) {
            if (counter == index)
                return current.data;
            counter++;
            current = current.next;
        }

        // when we get to this line, the caller was asking for a non-existent element so we assert fail
        assert (false);
        System.out.println("404 ~ NOT FOUND!");
        return -1;
    }

    @Override
    public String toString() {
        String result = "";
        Node current = head;
        while (current != null) {
            result += current.data;
            if (current.next != null) {
                result += ", ";
            }
            current = current.next;
        }
        if (result == "")
            return "List is empty";

        return "List with length " + len + ": " + result;
    }

}