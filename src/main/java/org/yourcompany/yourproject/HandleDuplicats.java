package org.yourcompany.yourproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.yourcompany.yourproject.AVLTree.DuplicateFileInfos;

public class HandleDuplicats {

public static HashMap<String, DuplicateFileInfos> setDuplicateFileInMap(Node root, DataAVLTreeInsert newDataToInsert, HashMap<String, DuplicateFileInfos> duplicateFilesMap) {
        if (root != null && newDataToInsert != null) {
            if (duplicateFilesMap.containsKey(newDataToInsert.hashedContent)) {
                DuplicateFileInfos duplicateFileInfos = duplicateFilesMap.get(newDataToInsert.hashedContent);
                if (newDataToInsert.creationDate.before(duplicateFileInfos.originalCreationDate)) {
                    duplicateFileInfos.DuplicateFileDirs.add(duplicateFileInfos.originalPath + "/" + duplicateFileInfos.originalFileName);
                    duplicateFileInfos.originalFileName = newDataToInsert.fileName;
                    duplicateFileInfos.originalPath = newDataToInsert.path;
                    duplicateFileInfos.originalCreationDate = newDataToInsert.creationDate;
                } else {
                    duplicateFileInfos.DuplicateFileDirs.add(newDataToInsert.path + "/" + newDataToInsert.fileName);
                }
            } else {
                DuplicateFileInfos duplicateFileInfos = new DuplicateFileInfos();
                duplicateFileInfos.originalFileName = root.fileName;
                duplicateFileInfos.originalPath = root.path;
                duplicateFileInfos.originalCreationDate = root.creationDate;
                duplicateFileInfos.DuplicateFileDirs.add(newDataToInsert.path + "/" + newDataToInsert.fileName);
                duplicateFilesMap.put(newDataToInsert.hashedContent, duplicateFileInfos);
            }
        }

        return duplicateFilesMap;
    }

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

    public static void deleteAllDuplicateFiles(HashMap<String, DuplicateFileInfos> duplicateFilesMap) {
        UserInterface ui = new UserInterface();
        Boolean deleteAllDuplicateFiles = ui.yesOrNoQuestion("Do you want to delete all duplicate files?");

        if (!deleteAllDuplicateFiles) {
            return;
        }
        
        for (String key : duplicateFilesMap.keySet()) {
            DuplicateFileInfos duplicateFileInfos = duplicateFilesMap.get(key);
            for (String dir : duplicateFileInfos.DuplicateFileDirs) {
                deleteDuplicateFile(key, dir);
            }
        }
    }
}
