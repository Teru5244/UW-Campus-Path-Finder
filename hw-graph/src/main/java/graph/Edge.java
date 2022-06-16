package graph;

/**
 * Edge is an immutable class that represents an edge of a node, which
 * only has a child node and a label of any object. It represents a node
 * points to a certain node with a label.
 */
public class Edge<T, E> {

    // Abstract Function:
    // AF(this) = an edge of a node that points to a child node with label.
    // Edge e such that e.childNode is the destination node and "start" points
    // to the destination node.
    //
    // Representation Invariants:
    // label != null && childNode != null

    private final Node<T> childNode;
    private final E label;

    /**
     * Construct a new edge with a child node and a string of label.
     *
     * @param childNode The child node that the node is pointing to.
     * @param label The label of the edge.
     * @spec.requires childNode != null and label != null
     * @spec.effects constructs a new edge (of a node) that points
     * to a certain node with label.
     */
    public Edge(Node<T> childNode, E label) {
        this.childNode = childNode;
        this.label = label;
        checkRep();
    }

    /**
     * Return the label of the edge.
     *
     * @return The label of the edge.
     */
    public E getLabel() {
        return label;
    }

    /**
     * Return the child node that the current node is pointing to.
     *
     * @return The child node of the current node in a Node object.
     */
    public Node<T> getChild() {
        return childNode;
    }

    /**
     * Check if two edges are the same.
     *
     * @param other The other edge to be compared with the current edge.
     * @return true if two edges have the same child nodes and labels.
     * @spec.requires param other is not null.
     */
    public boolean equals(Object other) {
        checkRep();
        if (!(other instanceof Edge<?,?>)) {
            return false;
        }
        Edge<?,?> e = (Edge<?,?>) other;
        e.checkRep();
        return this.childNode.equals(e.childNode) && this.label.equals(e.label);
    }

    /**
     * Returns a hash representation of the edge instance,
     * basically for HashSet and HashMap implementation.
     *
     * @return the hash code of the edge.
     */
    public int hashCode() {
        return childNode.hashCode() + label.hashCode();
    }

    /**
     * Check if the edge holds the representation invariants.
     *
     * @throws RuntimeException when the edge instance failed to
     * hold the representation invariants.
     */
    public void checkRep() {
        if (childNode == null) {
            throw new RuntimeException("child node is null");
        }
        if (label == null) {
            throw new RuntimeException("label is null");
        }
    }

}
