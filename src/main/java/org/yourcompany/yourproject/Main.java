/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.yourcompany.yourproject;

import org.yourcompany.yourproject.AVLTree.Node;

/**
 *
 * @author lukasdoll
 */
public class Main {

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        avlTree.delete(10);
        avlTree.delete(20);
        avlTree.delete(30);

        Node node = avlTree.find(20);
        if (node != null) {
            System.out.println("Data found in the tree");
        } else {
            System.out.println("Data not found in the tree");
        }
    }
}
