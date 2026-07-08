package org.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class FileClassifier {

    private final Properties rules = new Properties();

    public FileClassifier() {

        try (InputStream input =
                     getClass().getClassLoader().getResourceAsStream("rules.properties")) {

            if (input == null) {
                throw new IllegalStateException("rules.properties not found");
            }

            rules.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String classify(Path file) {

        String fileName = file.getFileName().toString();

        int dot = fileName.lastIndexOf('.');

        if (dot == -1)
            return "Others";

        String extension = fileName.substring(dot + 1).toLowerCase();

        return rules.getProperty(extension, "Others");

    }

}