package org.example;

import java.io.*;
import java.util.*;

public class FileCounter {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir");
        if (args.length > 0) {
            basePath = args[0];
        }

        File dir = new File(basePath, "src/main/java/org/example");

        if (!dir.isDirectory()) {
            System.out.println("Error: Directory not found: " + dir.getAbsolutePath());
            System.exit(1);
        }

        List<File> javaFiles = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile() && f.getName().toLowerCase().endsWith(".java")) {
                    javaFiles.add(f);
                }
            }
        }
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Number of Java files: " + javaFiles.size());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                int solvedCount = 0;
                for (File f : javaFiles) {
                    try {
                        Scanner scanner = new Scanner(f);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            if (line.contains("// Solved")) {
                                solvedCount++;
                            }
                        }
                        scanner.close();
                    } catch (IOException e) {
                        System.out.println("Cannot read file " + f.getName());
                    }
                }
                System.out.println("Number of Issues: " + solvedCount);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
