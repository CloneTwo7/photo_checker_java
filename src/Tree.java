import java.util.LinkedList;
/**
 * The following class will take in a LinkedList of any comparable data and sort it in a binary search tree
 * It doens't matter the type of object passed to it, as long as it is some form of Comparable<T>
 * @param <T>
 */
public class Tree<T extends Comparable<T>> {
    //The root node is the node at the top of the tree
    //It's important to note that the tree isn't guaranteed to be balanced.
    private Node<T> root;
    //The linked list is used to store the original data sent to it. it is used during
    //the construction of the binary search tree itself
    LinkedList<T> list;

    /**
     * Constructor for the Tree will take in LinkedList of a generic Comparable<T>
     * Then it will call the init method that will sort everything into where it needs to go
     * @param l
     */
    public Tree(LinkedList<T> l) { //Constructor by passing
        list = l; //Stores the LinkedList
        init(); //Initializes the tree itself
    }

    /**
     * This is another constructor that could be used. This constructor doesn't require a LinkedList,
     * instead it will just set whatever that first value is to be the root node, with the left and right
     * subtrees being null
     * @param value
     */
    public Tree(T value) { //Constructor by passing value
        root = new Node<T>(value); //sets root node to be new value
    }
    private void init() { //Intialize the Binary Search Tree using the Linked List passed to it
        for (int i = 0; i < list.size(); i++) { //For each element of the Linked List
            insert(list.get(i)); //Insert the value into the tree
        }
    }

    /**
     * The following checks if the root node is Empty
     * @return
     */
    public boolean isEmpty() { //Checks if the current root is empty
        return (root == null); //returns boolean value
    }

    /**
     * insert is used to add a value to the tree.
     * If the current root isn't empty, then it will pass the root
     * and the value to the other insert method, which will recursively
     * add the value to the tree.
     * @param value
     */
    public void insert(T value) { //inserting one value
        if(contains(value)) return; //Checks if the Tree already contains the value, allows for 0 repeated values
        if (isEmpty()) { //checks if the root is empty
            root = new Node<T>(value); //creates new node and adds it
        } else
            insert(root, value); //otherwise, adds it to the root node
    }

    /**
     * The following method takes in both a root node, and a value
     * It will recursively call the same method to see where the best location for
     * that value would be, inside of the tree itself
     * @param node
     * @param data
     */
    private void insert(Node<T> node, T data) { //adding something to another node
        int compared = data.compareTo(node.getValue());
        if (compared < 0) { //checks if it should be added to the left
            if (node.getLeft() == null) { //checks if left is null
                node.setLeft(new Node<T>(data)); //adds new node to the left
            } else { //otherwise...
                insert(node.getLeft(), data); //inserts it to the left of this node
            }
        } else { //otherwise it should be added to the right
            if (node.getRight() == null) { //checks if the right is null
                node.setRight(new Node<T>(data)); //adds new node to the right
            } else insert(node.getRight(), data);//otherwise it adds it to the right of that node
        }
    }

    /**
     * This method will return a boolean value based on whether or not
     * the piece of data being passed to it is contained within the tree
     *  @param data
     * @return
     */
    public boolean contains(T data) { //checking if data exists
        int compared = data.compareTo(root.getValue()); //stores compared value
        if (compared == 0) return true; //if identical, return true
        else if (compared < 0) return contains(root.getLeft(), data); //otherwise, search right OR
        else return contains(root.getRight(), data); //search left, depending on the compared value
    }

    /**
     * The following method is going to recursively search for the bit of data that was passed to it
     * If the data already exists in the BTS, then the method will return true, otherwise it will
     * return false.
     * @param node
     * @param data
     * @return
     */
    private boolean contains(Node<T> node, T data) { //Takes in a node and a value
        if (node == null) return false; //checks if the node is null, if so, return false (node does not exist in tree)
        int compared = data.compareTo(node.getValue()); //gathers the compared value
        if (compared == 0) return true; //If the values are equal, return true (it exists in the tree)
        else if (compared < 0 && node.getLeft() != null) { //otherwise search left OR
            return this.contains(node.getLeft(), data);
        } else if (compared > 0 && node.getRight() != null) { //otherwise search right
            return this.contains(node.getRight(), data);
        } else return false; //otherwise, return false (does not exist in the tree)
    }
}
//NONE OF THIS WORK MATTERED