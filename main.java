
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
    	TwoThreeTree bTree = new TwoThreeTree();
    	
        Scanner sc = new Scanner(new File("commands.txt"));
        String s;
        
        OUTER:
        while (true) {

            s = sc.nextLine();
            String[] tokens = s.split(" ");
            String command = tokens[0];

            switch (command) {
                case "I": {
                    int value = Integer.parseInt(tokens[1]);                
                    bTree.insert(value);
                    break;
                }
                case "B":
                	bTree.root.printKeys("");
                    break;
                case "E":
                    break OUTER;
                default:
                    break;
            }
        }
        

    	DynamicTable dynamicTable = new DynamicTable(2);
    	dynamicTable.insert(new Employee("Alice", 5000));
    	dynamicTable.insert(new Employee("Bob", 4000));
    	dynamicTable.insert(new Employee("Charlie", 6000));
    	dynamicTable.insert(new Employee("David", 4500));
    	dynamicTable.insert(new Employee("Eve", 5500));
        dynamicTable.printTree();
    }
}
