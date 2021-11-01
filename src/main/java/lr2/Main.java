package lr2;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        //FileManager f = new FileManager();
//        f.generateFile(10000);
        RBTree tree = new RBTree();
        tree.buildTree("./Data.csv");
        taskManager(tree);
    }

    public static void taskManager(RBTree tree){
        System.out.println("Select the action you want to perform: " +
                "\n 1. Print tree " +
                "\n 2. Add new record " +
                "\n 3. Delete the record " +
                "\n 4. Search for the record by key " +
                "\n 5. Edit the existing record");
        Scanner in = new Scanner(System.in);
        int command = in.nextInt();
        switch (command){
            case 1:
               tree.prettyPrint();
               break;
            case 2:
                System.out.println("Enter the key and value for it, separated with space");
                Scanner in2 = new Scanner(System.in);
                String newRecord = in2.nextLine();
                tree.userInsert(Integer.parseInt
                        (newRecord.substring(0,newRecord.indexOf(" "))),
                         newRecord.substring(newRecord.indexOf(" ") + 1));
//                System.out.println("Successfully added!");
                break;
            case 3:
                System.out.println("Enter the key of the record, you want to delete");
                Scanner in3 = new Scanner(System.in);
                int keyToByDeleted = in3.nextInt();
                tree.deleteNode(keyToByDeleted);
                System.out.println("Successfully deleted!");
                break;
            case 4:
                System.out.println("Enter the key of the record, you want to find");
                Scanner in4 = new Scanner(System.in);
                int keyToBeFound = in4.nextInt();
                System.out.println(tree.treeSearch(keyToBeFound));
                tree.printNumberOfComparisons();
                break;
            case 5:
                System.out.println("Enter the key of the record, you want to edit and new value, you want to add, separated with space");
                Scanner in5 = new Scanner(System.in);
                String editedRecord = in5.nextLine();
                tree.editRecord(Integer.parseInt
                                (editedRecord.substring(0,editedRecord.indexOf(" "))),
                                 editedRecord.substring(editedRecord.indexOf(" ") + 1));
                System.out.println("Successfully edited!");
                break;
            default:
                System.out.println("Wrong input");
        }
    }
}
