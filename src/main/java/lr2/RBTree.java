package lr2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * TODO:
 * 1. Insertion
 * 2. Deletion
 * 3. Search
 * 4. Editing data by key (search the key and change data)
 */
public class RBTree {
    private static final boolean RED = true;
    public static final boolean BLACK = false;

    private Node root;
    private Node NILL;


    public RBTree() {
        this.NILL = new Node();
        this.NILL.color = BLACK;
        this.NILL.leftChild = null;
        this.NILL.rightChild = null;
        this.NILL.parent = null;
        this.root = this.NILL;
    }

    public void buildTree(String fileName) throws IOException {
         try (Stream<String> stream = Files.lines(Path.of(fileName))){
            stream.forEach(this::processLineAndInsertInTree);
         }
    }

    private void processLineAndInsertInTree(String record){
        int key = Integer.parseInt(record.substring(0,record.indexOf(",")));
        String value = record.substring(record.indexOf(","));
        this.insert(key,value);
    }

    public void insert(int key, String data) {

        //BS insertion
        Node node = new Node();
        node.key = key;
        node.data = data;
        node.parent = null;
        node.leftChild = NILL;
        node.rightChild = NILL;
        node.color = RED;

        Node y = null;
        Node x = this.root;

        while (x != NILL) { //if the tree is empty^ it is skipped
            y = x;
            if (node.key < x.key) {
                x = x.leftChild;
            } else {
                x = x.rightChild;
            }
        }

        //y is parent of x
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.key < y.key) {
            y.leftChild = node;
        } else {
            y.rightChild = node;
        }

        if (node.parent == null) {
            node.color = BLACK;
            return;
        }

        rebalanceTree(node);

    }

    private void rebalanceTree(Node current) {
        Node uncle;
        while (current.parent.color == RED) {
            if (current.parent == current.parent.parent.rightChild) {
                uncle = current.parent.parent.leftChild;
                if (uncle.color == RED) { // parent and uncle both have RED color case 4(b)
                    uncle.color = BLACK;
                    current.parent.color = BLACK;
                    current.parent.parent.color = RED;
                    current = current.parent.parent;
                } else { // parent is RED and uncle is BLACK case 4(a)
                    if (current == current.parent.leftChild) { // LR rotation
                        current = current.parent;
                        rightRotate(current);
                    }
                    //RR rotation
                    current.parent.color = BLACK;
                    current.parent.parent.color = RED;
                    leftRotate(current.parent.parent);
                }
            } else { //the same, but mirrored
                uncle = current.parent.parent.rightChild;
                if (uncle.color == RED) { // parent and uncle both have RED color case 4(b)
                    uncle.color = BLACK;
                    current.parent.color = BLACK;
                    current.parent.parent.color = RED;
                    current = current.parent.parent;
                } else { // parent is RED and uncle is BLACK case 4(a)
                    if (current == current.parent.rightChild) { // RL rotation
                        current = current.parent;
                        leftRotate(current);
                    }
                    //RR rotation
                    current.parent.color = BLACK;
                    current.parent.parent.color = RED;
                    rightRotate(current.parent.parent);
                }
            }
            if(current == this.root) {
                break;
            }
        }
        this.root.color = BLACK;
    }

    private void leftRotate(Node node) {
        Node y = node.rightChild;
        node.rightChild = y.leftChild;
        if (y.leftChild != NILL) {
            y.leftChild.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null) {
            this.root = y;
        } else if (node == node.parent.leftChild) {
            node.parent.leftChild = y;
        } else {
            node.parent.rightChild = y;
        }
        y.leftChild = node;
        node.parent = y;
    }

    private void rightRotate(Node node) {
        Node y = node.leftChild;
        node.leftChild = y.rightChild;
        if (y.rightChild != NILL) {
            y.rightChild.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null) {
            this.root = y;
        } else if (node == node.parent.rightChild) {
            node.parent.rightChild = y;
        } else {
            node.parent.leftChild = y;
        }
        y.rightChild = node;
        node.parent = y;
    }

    public void prettyPrint() {
        printHelper(this.root, "", true);
    }
    private void printHelper(Node root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != NILL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.color ? "RED" : "BLACK";
            System.out.println(root.key + "(" + sColor + ")");
            printHelper(root.leftChild, indent, false);
            printHelper(root.rightChild, indent, true);
        }
    }


    class Node {

        int key;
        private String data; ///Later for file search
        boolean color;
        Node parent;
        Node leftChild;
        Node rightChild;

//        public Node(){
//            this.key = key;
////            this.color = RED;
//            this.parent = null;
//            this.rightChild = null;
//            this.leftChild = null;

//        }
    }
}
