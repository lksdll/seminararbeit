package org.yourcompany.yourproject;

import java.util.Date;

public class Node {
    public String key;
    public int height;
    public Node left;
    public Node right;

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