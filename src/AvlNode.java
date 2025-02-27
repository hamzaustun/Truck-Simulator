/**
 * This class represents nodes in avl tree
 */
public class AvlNode {
    AvlNode left;
    AvlNode right;
    int key;
    int height;

    /**
     * Create a node with a key
     * Height of a node is zero when it does not have a child
     */
    public AvlNode(int key) {
        this.key = key;
        this.height = 0;
    }
}
