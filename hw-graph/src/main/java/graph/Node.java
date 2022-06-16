package graph;

/**
 * This is the immutable class that represents a node in a graph. It contains
 * the data of the node as any object.
 */
public class Node<T> {

    // Abstract Function:
    // AF(this) = A node in a graph with data,
    //
    // Representation Invariant:
    // data != null

    private final T data;

    /**
     * Construct a new Node with given data "data".
     *
     * @param data The data within a node.
     * @spec.requires parameter data is not null.
     * @spec.effects constructs a new Node with given data.
     */
    public Node(T data) {
        this.data = data;
        checkRep();
    }

    /**
     * Get the value of data of a node.
     *
     * @return The data of the node.
     */
    public T getData() {
        return data;
    }

    /**
     * Returns true if two nodes have the same data value.
     *
     * @param other The other nodes to be compared with.
     * @return true if two nodes are the same, false otherwise.
     * @spec.requires other is not null.
     */
    public boolean equals(Object other) {
        checkRep();
        if (!(other instanceof Node<?>)) {
            return false;
        }
        Node<?> n = (Node<?>) other;
        n.checkRep();
        return this.data.equals(n.data);
    }

    /**
     * Returns a hash representation of the node instance,
     * basically for HashSet and HashMap implementation.
     *
     * @return the hash code of the node.
     */
    public int hashCode() {
        return data.hashCode();
    }

    /**
     * Check the representation invariant of a node.
     *
     * @throws RuntimeException if a node does not satisfy
     * the representation invariant.
     */
    public void checkRep() {
        if (this.data == null) {
            throw new RuntimeException("data is null");
        }
    }

}
