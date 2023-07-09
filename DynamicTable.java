class Employee {
    String name;
    double salary;
    
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
}

class TreeNode {
    int numKeys;
    String[] keys;
    Employee[] values;
    TreeNode[] children;
    
    public TreeNode(int order) {
        numKeys = 0;
        keys = new String[2 * order - 1];
        values = new Employee[2 * order - 1];
        children = new TreeNode[2 * order];
    }
    
    public boolean isLeaf() {
        return children[0] == null;
    }
    
}

class DynamicTable {
    private TreeNode root;
    private int order;
    
    public DynamicTable(int order) {
        this.order = order;
        root = new TreeNode(order);
    }
    
    public double search(String key) {
        return search(root, key);
    }
    
    private double search(TreeNode node, String key) {
        int i = 0;
        while (i < node.numKeys && key.compareTo(node.keys[i]) > 0) {
            i++;
        }
        
        if (i < node.numKeys && key.equals(node.keys[i])) {
            return node.values[i].salary;
        }
        
        if (node.isLeaf()) {
            return -1;
        } else {
            return search(node.children[i], key);
        }
    }
    
    public void insert(Employee employee) {
        if (root.numKeys == 2 * order - 1) {
            TreeNode newRoot = new TreeNode(order);
            newRoot.children[0] = root;
            root = newRoot;
            splitChild(newRoot, 0);
        }
        
        insertNonFull(root, employee);
    }
    
    private void insertNonFull(TreeNode node, Employee employee) {
        int i = node.numKeys - 1;
        
        if (node.isLeaf()) {
            while (i >= 0 && employee.name.compareTo(node.keys[i]) < 0) {
                node.keys[i + 1] = node.keys[i];
                node.values[i + 1] = node.values[i];
                i--;
            }
            
            node.keys[i + 1] = employee.name;
            node.values[i + 1] = employee;
            node.numKeys++;
        } else {
            while (i >= 0 && employee.name.compareTo(node.keys[i]) < 0) {
                i--;
            }
            
            i++;
            
            if (node.children[i].numKeys == 2 * order - 1) {
                splitChild(node, i);
                
                if (employee.name.compareTo(node.keys[i]) > 0) {
                    i++;
                }
            }
            
            insertNonFull(node.children[i], employee);
        }
    }
    
    private void splitChild(TreeNode parentTreeNode, int childIndex) {
        TreeNode childTreeNode = parentTreeNode.children[childIndex];
        TreeNode newTreeNode = new TreeNode(order);
        
        newTreeNode.numKeys = order - 1;
        
        for (int j = 0; j < order - 1; j++) {
            newTreeNode.keys[j] = childTreeNode.keys[j + order];
            newTreeNode.values[j] = childTreeNode.values[j + order];
        }
        
        if (!childTreeNode.isLeaf()) {
            for (int j = 0; j < order; j++) {
                newTreeNode.children[j] = childTreeNode.children[j + order];
            }
        }
        
        childTreeNode.numKeys = order - 1;
        
        for (int j = parentTreeNode.numKeys; j > childIndex; j--) {
            parentTreeNode.children[j + 1] = parentTreeNode.children[j];
        }
        
        parentTreeNode.children[childIndex + 1] = newTreeNode;
        
        for (int j = parentTreeNode.numKeys - 1; j >= childIndex; j--) {
            parentTreeNode.keys[j + 1] = parentTreeNode.keys[j];
            parentTreeNode.values[j + 1] = parentTreeNode.values[j];
        }
        
        parentTreeNode.keys[childIndex] = childTreeNode.keys[order - 1];
        parentTreeNode.values[childIndex] = childTreeNode.values[order - 1];
        parentTreeNode.numKeys++;
    }
    

    public void printTree() {
        printTree(root, "");
    }
    
    private void printTree(TreeNode node, String indent) {
        if (node == null) {
            return;
        }
        
        for (int i = 0; i < node.numKeys; i++) {
            System.out.println(indent + "- " + node.keys[i] + ": " + node.values[i].salary);
            
            if (!node.isLeaf()) {
                printTree(node.children[i], indent + "  ");
            }
        }
        
        if (!node.isLeaf()) {
            printTree(node.children[node.numKeys], indent + "  ");
        }
    }
}
