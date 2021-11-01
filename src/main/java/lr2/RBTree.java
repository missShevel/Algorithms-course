package lr2;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * TODO:
 * 1. Insertion (+)
 * 2. Deletion (+)
 * 3. Search (+)
 * 4. Editing data by key (search the key and change data)
 */
public class RBTree {
    private static final boolean RED = true;
    public static final boolean BLACK = false;

    private Node root;
    private Node NILL;

    private int numberOfComparisons = 0;


    public RBTree() {
        this.NILL = new Node();
        this.NILL.color = BLACK;
        this.NILL.leftChild = null;
        this.NILL.rightChild = null;
        this.NILL.parent = null;
        this.root = this.NILL;
    }

    public void buildTree(String fileName) throws IOException {
        try (Stream<String> stream = Files.lines(Path.of(fileName))) {
            stream.forEach(this::processLineAndInsertInTree);
        }
    }

    private void processLineAndInsertInTree(String record) {
        int key = Integer.parseInt(record.substring(0, record.indexOf(",")));
        String value = record.substring(record.indexOf(",") + 1);
        this.insert(key, value);
    }
    public void userInsert(int key, String data){
        insert(key,data);
        rerecordFile();
    }

    private void insert(int key, String data) {
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

        while (x != NILL) { //if the tree is empty it is skipped
            y = x;
            if (node.key < x.key) {
                x = x.leftChild;
            } else if (node.key > x.key){
                x = x.rightChild;
            } else {
                System.out.println("Record with such key already exists");
                return;
            }
        }

        //y is parent of x
        node.parent = y;
        if (y == null) {
            root = node;
        } else if(node.key > y.key) {
            y.rightChild = node;
        } else {
            y.leftChild = node;
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
            if (current == this.root) {
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

    public Node treeSearch(int key) {
        Node found = treeSearchHelper(this.root, key);
        if (found == NILL) {
            System.out.println("The record with this key cannot be found");

        }
        return found;
    }

    private Node treeSearchHelper(Node node, int key) {
        numberOfComparisons++;
        if (key == node.key || node == NILL) {
            return node;
        }
        if (key < node.key) {
            return treeSearchHelper(node.leftChild, key);
        }
        return treeSearchHelper(node.rightChild, key);
    }

    public void deleteNode(int key) {
        deleteNodeHelper(this.root, key);
        rerecordFile();
    }

    private void deleteNodeHelper(Node root, int key) {
        Node z = treeSearch(key);
        Node x, y;
        if (z == NILL) {
            return;
        }
        y = z;
        boolean colorOfY = y.color;
        if (z.leftChild == NILL) {
            x = z.rightChild;
            moveNode(z, z.rightChild);
        } else if (z.rightChild == NILL){
            x = z.leftChild;
            moveNode(z, z.leftChild);
        } else {
            y = minimalDescendant(z.rightChild);
            colorOfY = y.color;
            x = y.rightChild;
            if (y.parent == z) {
                x.parent = y;
            } else {
                moveNode(y, y.rightChild);
                y.rightChild = z.rightChild;
                y.rightChild.parent = y;
            }
            moveNode(z,y);
            y.leftChild = z.leftChild;
            y.leftChild.parent = y;
            y.color = z.color;
        }
        if(colorOfY == BLACK) {
            deletionBalance(x);
        }

    }

    private void deletionBalance(Node x) {
        Node s;
        while (x != root && x.color == BLACK) {
            if (x == x.parent.leftChild) {
                s = x.parent.rightChild;
                if (s.color == RED) {
                    // case 3.1
                    // x ’s sibling S is red
                    s.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    s = x.parent.rightChild;
                }

                if (s.leftChild.color == BLACK && s.rightChild.color == BLACK) {
                    // case 3.2
                    //x ’s sibling S is black, and both of S’s children are black. (
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.rightChild.color == BLACK) {
                        // case 3.3
                        // x ’s sibling S is black, S’s left child is red, and S’s right child is black.
                        s.leftChild.color = BLACK;
                        s.color = RED;
                        rightRotate(s);
                        s = x.parent.rightChild;
                    }

                    // case 3.4
                    //x ’s sibling S is black, and S’s right child is red.
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.rightChild.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.leftChild;
                if (s.color == RED) {
                    // case 3.1
                    //mirrored
                    s.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    s = x.parent.leftChild;
                }

                if (s.rightChild.color == BLACK && s.rightChild.color == BLACK) {
                    // case 3.2
                    //mirrored
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.leftChild.color == BLACK) {
                        // case 3.3
                        //mirrored
                        s.rightChild.color = BLACK;
                        s.color = RED;
                        leftRotate(s);
                        s = x.parent.leftChild;
                    }

                    // case 3.4
                    //mirrored
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.leftChild.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    private Node minimalDescendant(Node node) {
        while(node.leftChild != NILL) {
            node = node.leftChild;
        }
        return node;
    }

    private void moveNode(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.leftChild){
            u.parent.leftChild = v;
        } else {
            u.parent.rightChild = v;
        }
        v.parent = u.parent;
    }

    public void editRecord(int key, String newData){
        Node toBeCorrected = treeSearch(key);
        if(toBeCorrected == NILL) {
            return;
        }
        toBeCorrected.data = newData;
        rerecordFile();
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

    private void traverseTree(Node node, ArrayList<Node> nodes) {
        if (node != NILL) {
            traverseTree(node.leftChild, nodes);
            traverseTree(node.rightChild, nodes);
            nodes.add(node);

        }
    }

        public void rerecordFile() {
            ArrayList<Node> records = new ArrayList<>();
            traverseTree(this.root, records);
            try (FileWriter fstream = new FileWriter("Data.csv");

                 BufferedWriter info = new BufferedWriter(fstream)) {
                for (int i = 0; i < records.size(); i++) {
                    StringBuilder record = new StringBuilder();
                    record.append(records.get(i).key);
                    record.append(",");
                    record.append(records.get(i).data);
                    record.append("\n");
                    info.write(String.valueOf(record));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void printNumberOfComparisons(){
            System.out.println("Number of comparisons during search: " + numberOfComparisons);
        }

    }

    class Node {
        int key;
        String data;
        boolean color;
        Node parent;
        Node leftChild;
        Node rightChild;

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", data='" + data + '\'' +
                    '}';
        }
    }

