package org.yourcompany.yourproject;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

// Class representing an AVL Tree data structure for managing files and detecting duplicates.
public class AVLTree {

    // Root node of the AVL tree
    private Node root;

    // Instance of HandleDuplicats to manage duplicate file handling
    HandleDuplicats handleDuplicats = new HandleDuplicats();

    // Inner class to store information about duplicate files
    public static class DuplicateFileInfos {

        String originalFileName;
        String originalPath;
        Date originalCreationDate;
        HashSet<String> DuplicateFileDirs = new HashSet<>();
    }

    // Map to store duplicate files with their corresponding information
    public HashMap<String, DuplicateFileInfos> duplicateFilesMap = new HashMap<>();


    // Inserts a new data entry into the AVL tree
    public void insert(DataAVLTreeInsert data) {
        root = insert(root, data);
    }

    // Deletes a duplicate file from the map based on the key and directory
    public void deleteDuplicateFile(String key, String dir) {
        //duplicateFileMaphandleDuplicats.deleteDuplicateFile(key, dir, duplicateFilesMap);
    }

    // Returns the height of the AVL tree
    public int height() {
        return root == null ? -1 : root.height;
    }

    // Returns the root node of the AVL tree
    public Node getRoot() {
        return root;
    }

    // Calculates the balance factor of a node
    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    // Returns the map of duplicate files
    public HashMap<String, DuplicateFileInfos> getDuplicateFilesMap() {
        return duplicateFilesMap;
    }

    // Inserts a new node into the AVL tree and rebalances if necessary
    private Node insert(Node root, DataAVLTreeInsert newDataToInsert) {
        if (root == null) {
            return new Node(newDataToInsert.hashedContent, newDataToInsert.fileName, newDataToInsert.path, newDataToInsert.creationDate);
        } else if (root.key.compareTo(newDataToInsert.hashedContent) > 0) {
            root.left = insert(root.left, newDataToInsert);
        } else if (root.key.compareTo(newDataToInsert.hashedContent) < 0) {
            root.right = insert(root.right, newDataToInsert);
        } else if (root.key.compareTo(newDataToInsert.hashedContent) == 0) {
            // Handle duplicate file case
            duplicateFilesMap = handleDuplicats.setDuplicateFileInMap(root, newDataToInsert, duplicateFilesMap);
        }
        return rebalance(root); // Rebalance the tree after insertion
    }

    // Rebalances the AVL tree based on the balance factor of nodes
    private Node rebalance(Node z) {
        updateHeight(z); // Update the height of the node
        int balance = getBalance(z); // Get the balance factor
        // Right heavy case
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z); // Perform left rotation
            } else {
                z.right = rotateRight(z.right); // Perform right rotation on right child
                z = rotateLeft(z); // Perform left rotation
            }
        // Left heavy case
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right)) {
                z = rotateRight(z); // Perform right rotation
            } else {
                z.left = rotateLeft(z.left); // Perform left rotation on left child
                z = rotateRight(z); // Perform right rotation
            }
        }
        return z;
    }

    // Performs a right rotation on the given node
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y); // Update heights after rotation
        updateHeight(x);
        return x; // Return new root
    }

    // Performs a left rotation on the given node
    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y); // Update heights after rotation
        updateHeight(x);
        return x; // Return new root
    }

    // Updates the height of the given node
    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    // Returns the height of the given node
    private int height(Node n) {
        return n == null ? -1 : n.height;
    }

    // Finds a node with the specified key in the AVL tree
    public Node find(String key) {
        Node current = root;
        while (current != null) {
            if (current.key.equals(key)) {
                System.out.println("Data found: " + current.key);
                return current;
            }
            current = current.key.compareTo(key) < 0 ? current.right : current.left;
        }
        System.out.println("Data not found: " + key);
        return null;
    }

}
