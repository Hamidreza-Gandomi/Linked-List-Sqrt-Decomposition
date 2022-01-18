import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.sql.Time;
import java.util.Date;

import static java.lang.Math.sqrt;
import static java.lang.Math.floor;

public class Main {

    // used to print array of doubly linked list
    private static void print(DoublyLinkedList[] dll) {
        int size = dll.length;
        for (int i = 0; i < size; i++) {
            System.out.println(dll[i].toString());
        }
        System.out.println();
    }

    // used to calculate index of array in the array of linked list
    private static int indexCalculationForArray(int part, int partPlusOne, int indexToFind, int[] array) {
        int sumLength = 0, calculatedIndex = 0;
        for (int i = 0; i < (part + partPlusOne); i++) {
            sumLength += array[i];
            if (sumLength >= indexToFind) {
                calculatedIndex = i;
                break;
            }
        }
        return calculatedIndex;
    }

    // used to calculate index of linked list in the array of linked list
    private static int indexCalculationForLinkedList(int index, int indexToFind, int[] array) {
        int sumLength = 0, calculatedIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (sumLength < indexToFind)
                sumLength += array[i];
        }

        for (int j = array[index]; j > 0; j--) {
            if (sumLength == indexToFind) {
                calculatedIndex = j;
                break;
            }
            sumLength--;
        }

        return calculatedIndex;
    }

    /*** MAIN ***/
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        int numberOfRequests = input.nextInt();

        int radical, part, partPlusOne; // used to segment the list
        radical = (int) floor(sqrt(size));


        if (size == radical * radical) { // checks the number is complete square or not
            part = radical;
            partPlusOne = 0;
        } else {
            if (size - (radical * radical) > radical) {
                partPlusOne = size - ((radical + 1) * radical);
                part = radical - partPlusOne + 1;
            } else {
                partPlusOne = size - (radical * radical);
                part = radical - partPlusOne;
            }
        }

        DoublyLinkedList[] dll = new DoublyLinkedList[part + partPlusOne]; // array of doubly linked list
        for (int i = 0; i < (part + partPlusOne); i++) {
            //initialize by calling constructor for each index of array
            dll[i] = new DoublyLinkedList();
        }

        // used to save nodes who incoming from the console
        Node node1 = new Node();
        Node node2 = new Node();
        int data;

        // get data from the console
        for (int i = 0; i < part; i++) {
            for (int j = 0; j < radical; j++) {
                data = input.nextInt();

                if (j == 0) {
                    node1 = new Node(data, null, null);
                    dll[i].head = node1;
                    dll[i].tail = node1;
                } else {
                    node2 = new Node(data, null, node1);
                    node1.next = node2;
                    if (j == radical - 1)
                        dll[i].tail = node2;
                    else
                        node1 = node2;
                }
            }
            // adjust the length of each linked list
            dll[i].len = radical;
        }
        node1 = new Node();
        node2 = new Node();

        for (int i = part; i < (part + partPlusOne); i++) {
            for (int j = 0; j < radical + 1; j++) {
                data = input.nextInt();

                if (j == 0) {
                    node1 = new Node(data, null, null);
                    dll[i].head = node1;
                    dll[i].tail = node1;
                } else {
                    node2 = new Node(data, null, node1);
                    node1.next = node2;
                    if (j == radical)
                        dll[i].tail = node2;
                    else
                        node1 = node2;
                }
            }
            // adjust the length of each linked list
            dll[i].len = radical + 1;
        }

        int request; // number of requests

        // used to remember the initial length of the linked lists in any index of array of linked list
        int[] array = new int[part + partPlusOne];
        for (int i = 0; i < (part + partPlusOne); i++) {
            array[i] = dll[i].len;
        }

        int s, e, index_e, node_e, valueOf_e, index_s, node_s; // They are all used for request 1

        int indexToFind, Nth_Node, index; // They are all used for request 2

        // used to writing the data in the file output.txt
        FileWriter fw = new FileWriter("C:\\Users\\Hamidreza\\IdeaProjects\\LinkedList\\src\\output.txt");

        // to calculate the execution time of the program
        // long startTime = System.nanoTime();

        for (int i = 0; i < numberOfRequests; i++) {
            request = input.nextInt(); // Accepts only 1 and 2, prints "Your command could not be FOUND!" for other values.

            if (request == 1) {
                s = input.nextInt();
                e = input.nextInt();

                index_s = indexCalculationForArray(part, partPlusOne, s, array);
                node_s = indexCalculationForLinkedList(index_s, s, array);

                index_e = indexCalculationForArray(part, partPlusOne, e, array);
                node_e = indexCalculationForLinkedList(index_e, e, array);
                valueOf_e = dll[index_e].getData(node_e);

                // if two indexes given are in the same range
                if (index_s == index_e) {
                    // insert node with position e before node with position s
                    dll[index_s].insertAt(valueOf_e, node_s);
                    // delete node with position e + 1
                    dll[index_e].deleteAt(node_e + 1);
                } else {
                    // insert node with position e before node with position s
                    dll[index_s].insertAt(valueOf_e, node_s);
                    // delete node with position e
                    dll[index_e].deleteAt(node_e);
                }

                // adjust the length of any index at array of linked lists with initial lengths
                for (int j = 0; j < array.length; j++) {
                    if (dll[j].len != array[j]) {
                        dll[j + 1].insertAtFirst(dll[j].getData(dll[j].len));
                        dll[j].deleteEnd();
                    }
                }
            } else if (request == 2) {

                indexToFind = input.nextInt();

                index = indexCalculationForArray(part, partPlusOne, indexToFind, array);
                Nth_Node = indexCalculationForLinkedList(index, indexToFind, array);

                try {
                    // writing the data in the file
                    fw.write(dll[index].getData(Nth_Node) + "\n");
                } catch (Exception exception) {
                    System.out.println(exception);
                }
                // writing the data in the console
                System.out.println("The value of " + indexToFind + "th index is " + dll[index].getData(Nth_Node));
            } else {
                System.out.println("Your command could not be FOUND!");
            }

        }
        // used to close FileWriter and free memory
        fw.close();

        // to calculate the execution time of the program
        /**
         long endTime = System.nanoTime();
         long totalTime = endTime - startTime;
         double elapsedTimeInSecond = (double) totalTime / 1_000_000_000; // 1 second = 1_000_000_000 nano seconds
         System.out.println("Time: " + elapsedTimeInSecond + " seconds");
         **/
    }
}
// END
