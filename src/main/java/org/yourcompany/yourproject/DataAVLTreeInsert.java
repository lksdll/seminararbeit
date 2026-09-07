package org.yourcompany.yourproject;

import java.util.Date;

// Class representing the data structure for inserting into an AVL Tree.
public class DataAVLTreeInsert {
    // Attributes of the data to be stored in the AVL Tree
    String fileName;    // Name of the file
    String hashedContent;   // Hash of the file content
    String path;    // Path of the file
    Date creationDate;  // Creation date of the file

    public DataAVLTreeInsert(String fileName, String hashedContent, String path, Date creationDate) {
        this.fileName = fileName;
        this.hashedContent = hashedContent;
        this.path = path;
        this.creationDate = creationDate;
    }
}
