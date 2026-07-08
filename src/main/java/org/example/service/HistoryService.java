package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.FileMoveRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {

    private final ObjectMapper mapper = new ObjectMapper();

    private final Path historyFile = Path.of("history.json");

    public void save(List<FileMoveRecord> history) throws IOException {

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(historyFile.toFile(), history);

    }

    public List<FileMoveRecord> load() throws IOException {

        if (!Files.exists(historyFile)) {
            return new ArrayList<>();
        }

        return mapper.readValue(
                historyFile.toFile(),
                new TypeReference<List<FileMoveRecord>>() {
                }
        );

    }

    public void clear() throws IOException {

        if (Files.exists(historyFile)) {
            Files.delete(historyFile);
        }

    }

}