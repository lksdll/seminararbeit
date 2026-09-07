package org.yourcompany.yourproject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

// Class responsible for retrieving data from files in a specified directory.
public class GetData {

    // List to store all data from the files in the given directory
    private static ArrayList<DataAVLTreeInsert> dataForAVLTreeList = new ArrayList<>();

    // Public method to initiate data retrieval from files in a directory.
    // Accepts a directory path and a flag to indicate whether to search in subdirectories.
    public ArrayList<DataAVLTreeInsert> getDataFromFiles(String directoryPath, Boolean searchInSubDir) {
        File directory = new File(directoryPath);
        return getDataFromFiles(directory, searchInSubDir);
    }

    // Private method to recursively retrieve data from files in the specified directory.
    // If searchInSubDir is true, it will also search in subdirectories.
    private static ArrayList<DataAVLTreeInsert> getDataFromFiles(File directory, Boolean searchInSubDir) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isHidden()) { // Skip hidden files
                    if (file.isDirectory()) {
                        if (searchInSubDir) {
                            getDataFromFiles(file, true); // Recursive call for subdirectories
                        }
                    } else {
                        try (InputStream is = Files.newInputStream(file.toPath())) {
                            // Create a SHA-256 hash of the file content
                            MessageDigest md = MessageDigest.getInstance("SHA-256");
                            byte[] buffer = new byte[1024];
                            int numRead;
                            while ((numRead = is.read(buffer)) != -1) {
                                md.update(buffer, 0, numRead);
                            }
                            byte[] digest = md.digest();
                            String hash = bytesToHex(digest); // Convert hash to hex string

                            // Retrieve file attributes
                            Path filePath = Paths.get(file.getAbsolutePath());
                            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                            Date creationDate = new Date(attrs.creationTime().toMillis());
                            // Create a DataAVLTreeInsert object and add it to the list
                            DataAVLTreeInsert dataForAVLTree = new DataAVLTreeInsert(file.getName(), hash, file.getParent(), creationDate);

                            dataForAVLTreeList.add(dataForAVLTree);
                        } catch (IOException | NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return dataForAVLTreeList;
    }

    // Helper method to convert byte array to hex string
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0'); // Append leading zero for single digit hex
            }
            hexString.append(hex);
        }
        return hexString.toString(); // Return the hex string
    }
}
