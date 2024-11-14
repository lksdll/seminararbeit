package org.yourcompany.yourproject;

import java.util.Date;

// Data structure to store the name of the file, the SHA-256 hash of the file content and the directory of the file to insert it to the AVLTree
public class DataAVLTreeInsert {

    String fileName;
    String hashedContent;
    String path;
    Date creationDate;

    public DataAVLTreeInsert(String fileName, String hashedContent, String path, Date creationDate) {
        this.fileName = fileName;
        this.hashedContent = hashedContent;
        this.path = path;
        this.creationDate = creationDate;
    }
}
