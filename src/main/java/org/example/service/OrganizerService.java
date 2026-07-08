package org.example.service;

import org.example.model.FileMoveRecord;
import org.example.util.LoggerUtil;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganizerService {

    private final FileScanner scanner;
    private final FileClassifier classifier;
    private final FileMover mover;
    private final HistoryService historyService;

    public OrganizerService() {
        this.scanner = new FileScanner();
        this.classifier = new FileClassifier();
        this.mover = new FileMover();
        this.historyService = new HistoryService();
    }

    public void organize(Path folder) {

        List<FileMoveRecord> history = new ArrayList<>();

        try {

            List<Path> files = scanner.scan(folder);

            for (Path file : files) {

                try {

                    String category = classifier.classify(file);

                    Path destinationFolder = folder.resolve(category);

                    Path moved = mover.move(file, destinationFolder);

                    LoggerUtil.fileMoved(file, moved);

                    history.add(
                            new FileMoveRecord(
                                    file.toString(),
                                    moved.toString()
                            )
                    );

                } catch (AccessDeniedException e) {

                    LoggerUtil.error(e);

                }

                catch (FileAlreadyExistsException e) {

                    LoggerUtil.error(e);

                }

                catch (NoSuchFileException e) {

                    LoggerUtil.error(e);

                }

                catch (IOException e) {

                    LoggerUtil.error(e);

                }

            }

            historyService.save(history);

        } catch (IOException e) {

            LoggerUtil.error(e);

        }

    }

    public void undo() {

        try {

            List<FileMoveRecord> history = historyService.load();

            Collections.reverse(history);

            for (FileMoveRecord record : history) {

                try {

                    Path source = Path.of(record.getDestination());

                    Path destination = Path.of(record.getSource());

                    Files.createDirectories(destination.getParent());

                    Files.move(source, destination);

                    LoggerUtil.fileMoved(source, destination);

                } catch (IOException e) {

                    LoggerUtil.error(e);

                }

            }

            historyService.clear();

        } catch (AccessDeniedException e) {

            LoggerUtil.error(e);

        }

        catch (NoSuchFileException e) {

            LoggerUtil.error(e);

        }

        catch (IOException e) {

            LoggerUtil.error(e);

        }

    }

}