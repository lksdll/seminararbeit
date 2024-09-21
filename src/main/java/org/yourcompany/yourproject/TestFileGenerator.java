package org.yourcompany.yourproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class TestFileGenerator {
    public static void main(String[] args) {
        String directory = "/Users/lukasdoll/Documents/school/ft13a/Seminar/testing-folder/test-user-file-dir/files_quantity";
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            Path file = Paths.get(directory, "file" + i + ".txt");
            String content = String.valueOf(1 + random.nextInt(2000));

            try {
                Files.write(file, content.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}