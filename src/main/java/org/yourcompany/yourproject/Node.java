package org.yourcompany.yourproject;

import java.util.Date;

public class Node {
    String key;
    int height;
    Node left;
    Node right;

    String fileName;
    String path;
    Date creationDate;

    public Node(String key, String fileName, String path, Date creationDate) {
        this.key = key;
        this.fileName = fileName;
        this.path = path;
        this.creationDate = creationDate;
    }
}