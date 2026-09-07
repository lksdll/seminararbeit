package org.yourcompany.yourproject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import org.yourcompany.yourproject.AVLTree.DuplicateFileInfos;

public class UserInterface {

    private static Scanner scanner = new Scanner(System.in);
    
        // Displays a starting message to the user about the program's purpose.
        public static void start() {
            System.out.println("\n\nThis is a program to scan a directory of your choice for duplicate files");
        }
    
        // Prompts the user to enter a directory path and validates its existence.
        // If the directory does not exist, it prompts the user again.
        public static String getUserDir() {
            System.out.println("\n\nPlease enter the directory you would like to scan: ");
            String dir = scanner.nextLine();

        Path path = Paths.get(dir);
        // check if the provided directory exists
        if (Files.exists(path) && Files.isDirectory(path)) {
            return dir;
        } else {
            System.out.println("The provided directory does not exist. Please try again.");
            return getUserDir();
        }
    }

    // Prompts the user with a yes or no question and returns the response as a Boolean.
    // If the input is invalid, it asks the question again.
    public static Boolean yesOrNoQuestion(String question) {
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

    // Displays the duplicate files found along with the original (oldest) file.
    // If no duplicates are found, it informs the user.
    public static void printDuplicatFiles(HashMap<String, DuplicateFileInfos> duplicateFilesMap) {
        if (duplicateFilesMap.isEmpty()) {
            System.out.println("No duplicate files found");
        } else {
            System.out.println("Duplicate files found:");
            for (String fileHash : duplicateFilesMap.keySet()) {
                AVLTree.DuplicateFileInfos duplicateFileInfos = duplicateFilesMap.get(fileHash);
                System.out.println("\n\noriginal File: " + duplicateFileInfos.originalFileName + "\nPath: " + duplicateFileInfos.originalPath);
                System.out.println("\nDuplicates:");
                for (String duplicateFileDir : duplicateFileInfos.DuplicateFileDirs) {
                    System.out.println("  " + duplicateFileDir);
                }
            }
        }
    }
}
