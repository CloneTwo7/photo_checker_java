/**
 * The following class is a Node class designed for Comparable generic objects.
 * Each node consists of a bit of data, and a left and right node.
 * The nodes will typically be a part of a larger tree and will be constructed when
 * a Tree object is created.
 * @param <T>
 */
public class Node<T extends Comparable<T>> {
    //The following are the bits of data tied to each Node
    private T data; //This is the value stored at the node
    private Node<T> left; //This is the node to the left of the root
    private Node<T> right; //This is the node to the right of the root

    /**
     * Constructor for creating a new Node.
     * Data will be the data passed to it
     * left and right will both be set to null
     * @param newData
     */
    public Node(T newData) { //Takes in a value from the constructor
        this.data = newData; //sets data
        this.left = null; //sets left to be null
        this.right = null; //sets right to be null
    }

    /**
     * Method to return the value stored at the Node
     * @return
     */
    public T getValue() {
        return data;
    }

    /**
     * Method to set the value of the particular node
     * @param value
     */
    public void setValue(T value) {
        this.data = value;
    }

    /**
     * Method created to retrieve the left subtree
     * @return
     */
    public Node<T> getLeft() {
        return left;
    }

    /**
     * Method created to set the left subtree to a new node
     * @param left
     */
    public void setLeft(Node<T> left) {
        this.left = left;
    }

    /**
     * Method created to retrieve the right subtree
     * @return
     */
    public Node<T> getRight() {
        return right;
    }

    /**
     * Method created to set the right subtree to a new node
     * @param right
     */
    public void setRight(Node<T> right) {
        this.right = right;
    }
}
