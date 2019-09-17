package singlylinkedlist;

/**
 *  Modified from Drozdek, Data Structures and Algorithms in Java
 *
 *  Name: Olga Kochepasova
 *  COSC 311  FA19
 *  hw0912
 *  URL:  <your URL>
 * 
 * This code is a modified version of the base code given in homework 1
 */

public class SinglyLinkedList {

    // Inner Class
    public class Node {
        int   data;
        Node  next;
      
        public Node () {
            this (0, null);
        }
      
        public Node (int data) {
            this(data, null);
        }
      
        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
   }


    // Variables
    Node  head, tail;


    // Constructor
    public SinglyLinkedList() {
        head = tail = null;
    }


    // Methods
    public void insert(int data) {
        if (head == null) {
            head = tail = new Node(data);
        }
        else if (data <= head.data) {
            head = new Node(data, head);
        }
        else {
            Node p = null;
            Node n = head;

            while (n != null && data > n.data) {
                p = n;
                n = n.next;
            }

            p.next = new Node(data, n);
        }
    }

    public void delete(int data) {
        if (head != null) {
            Node p = null;
            Node n = head;

            while (n != null) {
                if (p == null && n.data == data) {
                    head = n.next;
                    return;
                }
                else if (n.data == data) {
                    p.next = n.next;
                    n.next = null;
                    return;
                }

                p = n;
                n = n.next;
            } // end while
        } // end if
    } // end delete


    public String  toString() {
        if (head == null) { 
            return "Empty String" ;
        }
        String s = "";
        for (Node p = head; p != null; p = p.next) 
            s += p.data +", ";
        return s;
    }


    // Main Method
    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();

        System.out.println("Execution begun");
        System.out.println("initial list: " + list );

        // testing
        list.insert(3);
        list.insert(5);
        System.out.println(list);

        list.insert(2);
        list.insert(2);
        list.insert(2);
        list.insert(7);
        list.insert(6);
        System.out.println("list after inserts: " + list);

        list.delete(2);
        list.delete(5);
        list.delete(7);
        System.out.println("list after deletes: " + list);

        System.out.println("Execution terminated");
    }
}