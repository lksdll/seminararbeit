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

public class GetData {

    // Data structure to store the name of the file, the SHA-256 hash of the file content and the directory of the file to insert it to the AVLTree
    public class DataForAVLTree {

        String fileName;
        String hashedContent;
        String path;
        Date creationDate;
    }
    // List to store all data from the files in the given directory
    private ArrayList<DataForAVLTree> dataForAVLTreeList = new ArrayList<>();

    public ArrayList<DataForAVLTree> getDataFromFiles(String directoryPath, Boolean searchInSubDir) {
        File directory = new File(directoryPath);
        return getDataFromFiles(directory, searchInSubDir);
    }

    // private method to set the data of the files in the right format in the list of Data (DataForAVLTree) in order to insert it into the AVLTree 
    private ArrayList<DataForAVLTree> getDataFromFiles(File directory, Boolean searchInSubDir) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isHidden()) {
                    if (file.isDirectory()) {
                        if (searchInSubDir) {
                            getDataFromFiles(file, true); // recursive call
                        }
                    } else {
                        try (InputStream is = Files.newInputStream(file.toPath())) {
                            MessageDigest md = MessageDigest.getInstance("SHA-256");
                            byte[] buffer = new byte[1024];
                            int numRead;
                            while ((numRead = is.read(buffer)) != -1) {
                                md.update(buffer, 0, numRead);
                            }
                            byte[] digest = md.digest();
                            String hash = bytesToHex(digest);

                            Path filePath = Paths.get(file.getAbsolutePath());
                            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                            Date creationDate = new Date(attrs.creationTime().toMillis());

                            DataForAVLTree dataForAVLTree = new DataForAVLTree();

                            dataForAVLTree.fileName = file.getName();
                            dataForAVLTree.hashedContent = hash;
                            dataForAVLTree.path = file.getParent();
                            dataForAVLTree.creationDate = creationDate;

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

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
