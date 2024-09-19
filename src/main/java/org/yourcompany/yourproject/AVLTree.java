package org.yourcompany.yourproject;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.yourcompany.yourproject.GetData.DataForAVLTree;

public class AVLTree {

    private Node root;

    HandleDuplicats handleDuplicats = new HandleDuplicats();

    public static class DuplicateFileInfos {

        String originalFileName;
        String originalPath;
        Date originalCreationDate;
        HashSet<String> DuplicateFileDirs = new HashSet<>();
    }

    public HashMap<String, DuplicateFileInfos> duplicateFilesMap = new HashMap<>();

    // --- METHODS FOR PUBLIC ACCESS --- \\
    public void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.println("Key: " + node.key + " File: " + node.fileName + " Path: " + node.path + " Creation Date: " + node.creationDate);
            printInOrder(node.right);
        }
    }

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

    public void insert(DataForAVLTree data) {
        root = insert(root, data);
    }

    public void deleteDuplicateFile(String key, String dir) {
        //duplicateFileMaphandleDuplicats.deleteDuplicateFile(key, dir, duplicateFilesMap);
    }

    public int height() {
        return root == null ? -1 : root.height;
    }

    public Node getRoot() {
        return root;
    }

    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    public HashMap<String, DuplicateFileInfos> getDuplicateFilesMap() {
        return duplicateFilesMap;
    }

    // --- PRIVATE METHODS WITH THE LOGIC OF THE AVL TREE --- \\
    private Node insert(Node root, DataForAVLTree newDataToInsert) {
        if (root == null) {
            return new Node(newDataToInsert.hashedContent, newDataToInsert.fileName, newDataToInsert.path, newDataToInsert.creationDate);
        } else if (root.key.compareTo(newDataToInsert.hashedContent) < 0) {
            root.left = insert(root.left, newDataToInsert);
        } else if (root.key.compareTo(newDataToInsert.hashedContent) > 0) {
            root.right = insert(root.right, newDataToInsert);
        } else if (root.key.compareTo(newDataToInsert.hashedContent) == 0) {
            duplicateFilesMap = handleDuplicats.setDuplicateFileInMap(root, newDataToInsert, duplicateFilesMap);
        }
        return rebalance(root);
    }

    private Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right)) {
                z = rotateRight(z);
            } else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(Node n) {
        return n == null ? -1 : n.height;
    }
}
