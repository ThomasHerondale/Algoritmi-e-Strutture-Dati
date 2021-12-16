package tree.linked;

import tree.Tree;

/**
 * A set of all the possible behaviors of many classes implementing the {@link Tree} interface,
 * relating to their {@link Tree#delete(Object)} methods.
 */
public enum TreeDeleteMode {
    /**
     * Sets the {@link Tree#delete(Object)} method to simply throw an {@link UnsupportedOperationException}.
     */
    UNSUPPORTED,
    /**
     * Sets the {@link Tree#delete(Object)} method to only delete leaf-nodes. Any attempt to delete a non-leaf node will result in an
     * {@link UnsupportedOperationException} being thrown.
     */
    LEAVES_ONLY,
    /**
     * Sets the {@link Tree#delete(Object)} method to delete the specified node and reassigns any children to other random node(s) in the tree.
     */
    CHILDREN_TO_RANDOM,
    /**
     * Sets the {@link Tree#delete(Object)} method to delete the specified node and all of its subtree. Note that
     * if this is the currently selected behavior, any attempt to delete the root node will result
     * in an {@link IllegalArgumentException}.
     */
    SUBTREE;
}
