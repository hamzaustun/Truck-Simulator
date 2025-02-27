/**
 * This class is to store the parking lots by their capacity constraints
 * It has a root node
 */

public class AVLTree {
    public AvlNode root;

    public AVLTree() {
    }

    public void insert(int capacityConstraint) {
        root = insert(root, capacityConstraint);// the main node is root
    }

    private AvlNode insert(AvlNode node, int capacityConstraint) { // function to insert new parking lot into avl tree that the root of it is the node
        if (node == null) { // if node does not exist
            return new AvlNode(capacityConstraint);
        }
        // search a place for the given capacity constraint by moving left or right child
        if (capacityConstraint > node.key) {
            node.right = insert(node.right, capacityConstraint);
        } else if (capacityConstraint < node.key) {
            node.left = insert(node.left, capacityConstraint);
        } else {
            return node; // Duplicate; do nothing
        }

        return balanceANode(node); // update the height of the node and balance the node
    }

    public void delete(int capacityConstraint) {
        root = delete(root, capacityConstraint); // the main node is root
    }

    private AvlNode delete(AvlNode node, int capacityConstraint) {// function to delete the given parking lot from avl tree that the root of it is the node
        if (node == null) {
            return null;
        }
        // search the given capacity constraint by moving left or right child
        if (capacityConstraint > node.key) {
            node.right = delete(node.right, capacityConstraint);
        } else if (capacityConstraint < node.key) {
            node.left = delete(node.left, capacityConstraint);
        } else { // the searched node is found
            if (node.left == null || node.right == null) { // if it does not have two children
                if (node.left != null) {
                    node = node.left; // put the left child in the place of its parent
                } else {
                    node = node.right; // put the right child in the place of its parent
                }

            } else { // if it has two children
                AvlNode smallestLarger = findMin(node.right); // find the min value in right subtree
                node.key = smallestLarger.key; // change the node with smallest larger node in the right subtree
                node.right = delete(node.right, smallestLarger.key); // delete the smallest larger node from right subtree
            }
        }

        // If a null child replaced its parent, return it
        if (node == null) {
            return null;
        }

        return balanceANode(node);// update the height of the node and balance the node
    }


    private AvlNode findMin(AvlNode node) {// find the minimum value in the subtree with the given node as root
        AvlNode current = node;
        while (current.left != null) { // go left until the left child is null
            current = current.left;
        }
        return current;
    }


    private AvlNode balanceANode(AvlNode node) { // to balance a given node
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);

        if (balance > 1) { // if the height of the left subtree is greater than the right
            if (getBalance(node.left) < 0) { // left-right rotation case
                node.left = rotateWithRightChild(node.left);
            }
            return rotateWithLeftChild(node); // left-left rotation case
        }

        if (balance < -1) { // if the height of the right subtree is greater than the left
            if (getBalance(node.right) > 0) { // right-left rotation case
                node.right = rotateWithLeftChild(node.right);
            }
            return rotateWithRightChild(node); // right-right rotation case
        }

        return node; // return balanced node
    }

    private AvlNode rotateWithLeftChild(AvlNode parent) {
        AvlNode newParent = parent.left; // the newParent will be left child
        AvlNode newLeft = newParent.right; // new left of the former parent

        // do rotation
        newParent.right = parent;
        parent.left = newLeft;

        // update heights
        parent.height=1 + Math.max(getHeight(parent.left), getHeight(parent.right));
        newParent.height = 1 + Math.max(getHeight(newParent.left), getHeight(newParent.right));

        return newParent;
    }

    private AvlNode rotateWithRightChild(AvlNode parent) {
        AvlNode newParent = parent.right;// the newParent will be right child
        AvlNode newRight = newParent.left; // new right of the former parent

        // do rotation
        newParent.left = parent;
        parent.right = newRight;

        // update heights
        parent.height= 1 + Math.max(getHeight(parent.left), getHeight(parent.right));
        newParent.height = 1 + Math.max(getHeight(newParent.left), getHeight(newParent.right));

        return newParent;
    }



    private int getHeight(AvlNode node) {
        if (node == null)
            return 0; // null nodes have height 0
        return node.height; // return the height of a given node
    }


    private int getBalance(AvlNode node) { // to calculate the balance of a given node
        if (node == null)
            return 0; // null nodes have balance 0
        return getHeight(node.left) - getHeight(node.right);
    }
}

