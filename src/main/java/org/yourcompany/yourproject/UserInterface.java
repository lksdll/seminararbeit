package org.yourcompany.yourproject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import org.yourcompany.yourproject.AVLTree.DuplicateFileInfos;

public class UserInterface {

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("This is a program to scan a directory of your choice for duplicate files");
    }

    public String getUserDir() {
        System.out.println("\n\nPlease enter the directory you would like to scan: ");
        String dir = scanner.nextLine();

        Path path = Paths.get(dir);
        if (Files.exists(path) && Files.isDirectory(path)) {
            return dir;
        } else {
            System.out.println("The provided directory does not exist. Please try again.");
            return getUserDir(); // ask for input again
        }
    }

    public Boolean yesOrNoQuestion(String question) {
        System.out.println(question + " (y/n)");
        String val = scanner.next();
        if (val.equalsIgnoreCase("y") || val.equalsIgnoreCase("yes")) {
            return true;
        } else if (val.equalsIgnoreCase("n") || val.equalsIgnoreCase("no")) {
            return false;
        } else {
            System.out.println("Invalid input. Pleas answer with yes or no.");
            return yesOrNoQuestion(question); // ask for input again
        }
    }

    public void printDuplicatFiles(HashMap<String, DuplicateFileInfos> duplicateFilesMap) {
        if (duplicateFilesMap.isEmpty()) {
            System.out.println("No duplicate files found");
        } else {
            System.out.println("Duplicate files found:");
            for (String fileName : duplicateFilesMap.keySet()) {
                AVLTree.DuplicateFileInfos duplicateFileInfos = duplicateFilesMap.get(fileName);
                System.out.println("File: " + fileName);
                for (String duplicateFileDir : duplicateFileInfos.DuplicateFileDirs) {
                    System.out.println("  " + duplicateFileDir);
                }
            }
        }
    }
}
