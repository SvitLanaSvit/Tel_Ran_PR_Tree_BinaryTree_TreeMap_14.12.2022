import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;


public class Node {
    public Integer value;
    public Node left;
    public Node right;

    private static boolean isNotExists(Node node) {
        return node == null || node.value == null;
    }

    private static void createNode(Node node, int value) {
        node.left = new Node();
        node.right = new Node();
        node.value = value;
    }

    private static void insert(Node node, int value) {
        if (isNotExists(node))
            createNode(node, value);
        else if (value < node.value)
            insert(node.left, value);
        else
            insert(node.right, value);
    }

    private static Node search(Node node, int value) {
        if (isNotExists(node)) {
            return null;
        }
        if (node.value == value) {
            return node;
        }
        if (value < node.value) {
            return search(node.left, value);
        }
        return search(node.right, value);
    }

    private static Node getMin(Node node) {
        if (isNotExists(node)) return null;
        if (isNotExists(node.left)) return node;
        return getMin(node.left);
    }

    private static Node getMax(Node node) {
        if (isNotExists(node)) return null;
        if (isNotExists(node.right)) return node;
        return getMax(node.right);
    }

    private static void inOrderTraversal(Node node) { //symmetric
        if (isNotExists(node)) return;
        inOrderTraversal(node.left);
        System.out.print(node.value + "->");
        inOrderTraversal(node.right);
    }

    private static void postOrderTraversal(Node node) { //inverted
        if(isNotExists(node)){
            return;
        }
        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        System.out.print(node.value + "->");
    }

    private static void directOrderTraversal(Node node) { //direct
        if(isNotExists(node)){
            return;
        }
        System.out.print(node.value + "->");
        directOrderTraversal(node.left);
        directOrderTraversal(node.right);
    }

    private static void moveNode(Node toNode, Node fromNode) {
        toNode.value = fromNode.value;
        toNode.left = fromNode.left;
        toNode.right = fromNode.right;
    }

    private static int getSize(Node node){
        if (isNotExists(node))
            return 0;

        // Initialize empty queue.
        Queue<Node> queue = new LinkedList<Node>();

        // Do level order traversal starting from root
        queue.add(node);

        int count = 0; // Initialize count of full nodes
        while (!queue.isEmpty())
        {
            Node temp = queue.poll();
            if (temp.left != null && temp.right != null)
                count++;

            // Enqueue left child
            if (temp.left != null)
            {
                queue.add(temp.left);
            }

            // Enqueue right child
            if (temp.right != null)
            {
                queue.add(temp.right);
            }
        }
        return count;
    }

    private static int getChildrenCount(Node node) {
        int count = 0;
        if (!isNotExists(node.left)) {
            count += 1;
        }
        if (!isNotExists(node.right)) {
            count += 1;
        }
        return count;
    }

    private static boolean remove(Node root, int value){
        Node nodeToDelete = search(root, value);
        if(isNotExists(nodeToDelete)) {
            return false;
        }
        int childrenCount = getChildrenCount(nodeToDelete);
        if(childrenCount < 2) {
            removeNodeWithOneOrChildWithoutChildren(nodeToDelete);
        }
        else{
            Node minNode = getMin(nodeToDelete.right);
            assert minNode != null;
            nodeToDelete.value = minNode.value;
            removeNodeWithOneOrChildWithoutChildren(minNode);
        }
        return true;
    }

    private static void removeNodeWithOneOrChildWithoutChildren(Node nodeToDelete){
        Node childOrNull = getChildOrNull(nodeToDelete);
        moveNode(nodeToDelete, childOrNull);
    }

    private static Node getChildOrNull(Node node){
        return isNotExists(node.left) ? node.right : node.left;
    }

    public static void main(String[] args) {
        Integer[] digit = {9, 2, 5, 13, 6, 10, 14, 7, 33, 44, 3};
        Node node = new Node();
        createNode(node, 9);
        for (int i = 1; i < digit.length; i++) {
            insert(node, digit[i]);
        }
        inOrderTraversal(node);
        System.out.println();
        postOrderTraversal(node);
        System.out.println();
        directOrderTraversal(node);
        System.out.println();
        System.out.println("Count all children of node: " + getChildrenCount(node));
        Node nodeMin = getMin(node);
        assert nodeMin != null;
        System.out.println("Min : " + nodeMin.value);
        Node nodeMax = getMax(node);
        assert nodeMax != null;
        System.out.println("Max : " + nodeMax.value);
        remove(node, 10);
        inOrderTraversal(node);
        System.out.println();
        int searchVal = 5;
        System.out.println("Address of search Node by value '" + searchVal + "': " + search(node, searchVal));
    }
}
