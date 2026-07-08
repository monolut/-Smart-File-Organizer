package org.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileMover {

    public Path move(Path source, Path destinationFolder) throws IOException {

        Files.createDirectories(destinationFolder);

        Path destination = destinationFolder.resolve(source.getFileName());

        destination = resolveConflict(destination);

        return Files.move(
                source,
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );
    }

    private Path resolveConflict(Path destination) {

        if (!Files.exists(destination)) {
            return destination;
        }

        String fileName = destination.getFileName().toString();

        int dot = fileName.lastIndexOf('.');

        String name;
        String extension;

        if (dot == -1) {
            name = fileName;
            extension = "";
        } else {
            name = fileName.substring(0, dot);
            extension = fileName.substring(dot);
        }

        int counter = 1;

        Path parent = destination.getParent();

        while (true) {

            Path newPath = parent.resolve(
                    name + "(" + counter + ")" + extension
            );

            if (!Files.exists(newPath)) {
                return newPath;
            }

            counter++;
        }
    }
}