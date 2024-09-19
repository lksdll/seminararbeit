package org.yourcompany.yourproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.yourcompany.yourproject.AVLTree.DuplicateFileInfos;
import org.yourcompany.yourproject.GetData.DataForAVLTree;

public class HandleDuplicats {

public HashMap<String, DuplicateFileInfos> setDuplicateFileInMap(Node root, DataForAVLTree newDataToInsert, HashMap<String, DuplicateFileInfos> duplicateFilesMap) {
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

    public String deleteDuplicateFile(String key, String dir) {
        try {
            Path pathToFile = Paths.get(dir);
            Files.deleteIfExists(pathToFile);
        } catch (IOException e) {
            System.err.println("Unable to delete file: " + dir);
            e.printStackTrace();
        }
        return dir;
    }
}
