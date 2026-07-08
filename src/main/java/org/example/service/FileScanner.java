package org.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileScanner {

    public List<Path> scan(Path folder) throws IOException {

        try (var stream = Files.list(folder)) {

            return stream
                    .filter(Files::isRegularFile)
                    .toList();

        }

    }

}
