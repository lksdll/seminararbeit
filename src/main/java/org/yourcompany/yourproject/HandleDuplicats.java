package org.yourcompany.yourproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.yourcompany.yourproject.AVLTree.DuplicateFileInfos;

public class HandleDuplicats {

// This method checks if a file is a duplicate while being inserted into the AVL Tree.
    // If a duplicate is found, it stores the information in a HashMap along with details about the original file.
public static HashMap<String, DuplicateFileInfos> setDuplicateFileInMap(Node root, DataAVLTreeInsert newDataToInsert, HashMap<String, DuplicateFileInfos> duplicateFilesMap) {
        if (root != null && newDataToInsert != null) {
            // Check if the hashed content already exists in the map
            if (duplicateFilesMap.containsKey(newDataToInsert.hashedContent)) {
                DuplicateFileInfos duplicateFileInfos = duplicateFilesMap.get(newDataToInsert.hashedContent);
                // Update the original file information if the new file is older
                if (newDataToInsert.creationDate.before(duplicateFileInfos.originalCreationDate)) {
                    duplicateFileInfos.DuplicateFileDirs.add(duplicateFileInfos.originalPath + "/" + duplicateFileInfos.originalFileName);
                    duplicateFileInfos.originalFileName = newDataToInsert.fileName;
                    duplicateFileInfos.originalPath = newDataToInsert.path;
                    duplicateFileInfos.originalCreationDate = newDataToInsert.creationDate;
                } else {
                    // Add the new duplicate file directory to the list
                    duplicateFileInfos.DuplicateFileDirs.add(newDataToInsert.path + "/" + newDataToInsert.fileName);
                }
            } else {
                // Create a new DuplicateFileInfos object for a new duplicate
                DuplicateFileInfos duplicateFileInfos = new DuplicateFileInfos();
                duplicateFileInfos.originalFileName = root.fileName;
                duplicateFileInfos.originalPath = root.path;
                duplicateFileInfos.originalCreationDate = root.creationDate;
                duplicateFileInfos.DuplicateFileDirs.add(newDataToInsert.path + "/" + newDataToInsert.fileName);
                // Store the new duplicate file information in the map
                duplicateFilesMap.put(newDataToInsert.hashedContent, duplicateFileInfos);
            }
        }

        return duplicateFilesMap;
    }

    // Method to delete a specific duplicate file based on its directory path.
    public static String deleteDuplicateFile(String key, String dir) {
        try {
            Path pathToFile = Paths.get(dir);
            Files.deleteIfExists(pathToFile);
        } catch (IOException e) {
            System.err.println("Unable to delete file: " + dir);
            e.printStackTrace();
        }
        return dir;
    }

    // Method to delete all duplicate files stored in the provided HashMap.
    public static void deleteAllDuplicateFiles(HashMap<String, DuplicateFileInfos> duplicateFilesMap) {
        UserInterface ui = new UserInterface();
        Boolean deleteAllDuplicateFiles = ui.yesOrNoQuestion("Do you want to delete all duplicate files?");

        if (!deleteAllDuplicateFiles) {
            return;
        }
        
        // Iterate through the map and delete each duplicate file
        for (String key : duplicateFilesMap.keySet()) {
            DuplicateFileInfos duplicateFileInfos = duplicateFilesMap.get(key);
            for (String dir : duplicateFileInfos.DuplicateFileDirs) {
                deleteDuplicateFile(key, dir);
            }
        }
    }
}
