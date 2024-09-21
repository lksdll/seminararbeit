/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.yourcompany.yourproject;

/**
 *
 * @author lukasdoll
 */
public class Main {

    public static void main(String[] args) {

        UserInterface ui = new UserInterface();
        // Show a little welcoming message to the user in the console
        ui.start();

        // Get the directory from the user in the console
        String userDir = ui.getUserDir();

        // Ask the user, if he wants to scann subdirectories as well
        Boolean scanSubDir = ui.yesOrNoQuestion("Should subdirectorys inside of the directory you provided also be scaned?");

        GetData getData = new GetData();
        AVLTree avlTree = new AVLTree();

        // Insert all data from the files in the given directory into the AVLTree
        for (GetData.DataForAVLTree data : getData.getDataFromFiles(userDir, scanSubDir)) {
            avlTree.insert(data);
        }

        ui.printDuplicatFiles(avlTree.getDuplicateFilesMap());
        HandleDuplicats handleDuplicats = new HandleDuplicats();
        handleDuplicats.deleteAllDuplicateFiles(avlTree.getDuplicateFilesMap());
    }
}
