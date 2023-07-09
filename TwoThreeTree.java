class Node {
    int[] keys;
    Node[] children;
    int keysCount;
    private boolean isLeaf;
    
    boolean isLeaf() {
    	return this.isLeaf;
    }

    boolean isNotLeaf() {
    	return !this.isLeaf;
    }
    
    static int MIN_KEYS = 2;

    public Node(boolean isLeaf) {
    	// Cada nó pode ter no máximo três chaves
        this.keys = new int[3];
        this.children = new Node[4];
        // O nó começa com um total de zero chaves
        this.keysCount = 0;
        
        this.isLeaf = isLeaf;
    }
    
    public void setFirstChild(Node node) {
    	this.children[0] = node;
    }
    
    public int sortLeafAscending(int index, int key) {
        while (index >= 0 && keys[index] > key) {
            keys[index + 1] = keys[index];
            index--;
        }
        return index;
        
    }

    public void insert(int key) {
    	// Índice da última chave do nó
        int index = keysCount - 1;
        

        // Se for nó folha
        if (this.isLeaf()) {
        	// Ordena o array (menor para esquerda e maior para direita)
        	// e obtém o índice da menor chave em relação a nova chave
        	int minorKeyIndex = this.sortLeafAscending(index, key);
        	keys[minorKeyIndex + 1] = key;
            keysCount++;
        } 
        
        // Se não for nó folha (ou seja, tem filhos)
        else {
        	
        	// Procura o índice do filho em que a chave do
        	// nó seja menor do que a chave a ser inserida
            while (index >= 0 && keys[index] > key) {
                index--;
            }
            
            // Nó em que a chave será inserida
            Node rightNode = children[index + 1];

            // Se neste nó já possuir três chaves, hora de diví-lo
            if (rightNode.keysCount == 3) {
                splitChild(index + 1, rightNode);

                if (keys[index + 1] < key) {
                    index++;
                }
            }
            
            // Insere a nova chave na posição mais a direita do nó
            children[index + 1].insert(key);
        }
    }

    public void splitChild(int index, Node child) {
        Node newNode = new Node(child.isLeaf);
        newNode.keysCount = 1;

        // Define a chave do nó com base na
        // chave de maior valor do nó pai
        newNode.keys[0] = child.keys[2];

        if (this.isNotLeaf()) {
            for (int j = 0; j < MIN_KEYS; j++) {
                newNode.children[j] = child.children[j + MIN_KEYS];
            }
        }
        
        // Seta a quantidade de chaves para o padrão
        child.keysCount = 1;

        for (int j = keysCount; j > index; j--) {
            children[j + 1] = children[j];
        }

        // Define o nó mais a direita com base no novo nó splitado
        children[index + 1] = newNode;

        for (int j = keysCount - 1; j >= index; j--) {
            keys[j + 1] = keys[j];
        }

        // Seta a chave do nó "pai" como o valor do meio
        keys[index] = child.keys[1];
        keysCount++;
    }

    public void printKeys(String prefix) {
        System.out.print(prefix);

        // Exibe as chaves do nó entre parênteses
        System.out.print("(");
        for (int i = 0; i < keysCount; i++) {
            System.out.print(keys[i]);
            if (i < keysCount - 1) {
                System.out.print(", ");
            }
        }
        System.out.print(")");
        System.out.println();

        if (this.isNotLeaf()) {
            // Exibe os filhos do nó entre colchetes
            for (int i = 0; i < keysCount + 1; i++) {
                String childPrefix = prefix + "  ";
                System.out.print(childPrefix + "[Child " + i + "]: ");
                children[i].printKeys(childPrefix);
            }
        }
    }
}

public class TwoThreeTree {
    Node root = null;
    static int FIRST_NODE_INDEX = 0;
    static int FIRST_KEY_INDEX = 0;
    static boolean IS_NOT_LEAF = false;
    
    private void initializeTree(int key) {
    	root = new Node(true);
        root.keys[FIRST_KEY_INDEX] = key;
        root.keysCount = 1;
    }
    
    private boolean isBeginOfTree() {
    	return root == null;
    }
    
    private boolean isRootWithThreeKeys() {
    	return root.keysCount == 3;
    }

    public void insert(int key) {
        if (this.isBeginOfTree()) {
            this.initializeTree(key);
        } else {
            if (this.isRootWithThreeKeys()) {
                Node newRoot = new Node(IS_NOT_LEAF);
                newRoot.setFirstChild(root);
                newRoot.splitChild(FIRST_NODE_INDEX, root);
                newRoot.insert(key);
                root = newRoot;
            } else {
                root.insert(key);
            }
        }
    }
}