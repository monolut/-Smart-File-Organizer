package org.example.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.service.OrganizerService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class OrganizerApplication extends Application {

    private final OrganizerService organizer = new OrganizerService();

    private TextField folderField;

    private Label statusLabel;

    @Override
    public void start(Stage stage) {

        stage.setTitle("Smart File Organizer");

        Label title = new Label("Smart File Organizer");
        title.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        Label folderLabel = new Label("Folder:");

        folderField = new TextField();
        folderField.setPrefWidth(350);

        Button browseButton = new Button("Browse");

        HBox folderBox = new HBox(10);

        folderBox.getChildren().addAll(folderField, browseButton);

        folderBox.setAlignment(Pos.CENTER_LEFT);

        Button organizeButton = new Button("Organize");

        Button undoButton = new Button("Undo");

        HBox buttons = new HBox(15);

        buttons.getChildren().addAll(organizeButton, undoButton);

        statusLabel = new Label("Ready");

        VBox root = new VBox(15);

        root.setPadding(new Insets(20));

        root.getChildren().addAll(
                title,
                folderLabel,
                folderBox,
                buttons,
                statusLabel
        );

        browseButton.setOnAction(event -> {

            DirectoryChooser chooser = new DirectoryChooser();

            chooser.setTitle("Choose folder");

            File selected = chooser.showDialog(stage);

            if (selected != null) {

                folderField.setText(selected.getAbsolutePath());

            }

        });

        organizeButton.setOnAction(event -> {

            String folder = folderField.getText();

            if (folder.isBlank()) {

                showError("Please choose a folder.");

                return;

            }

            Path path = Path.of(folder);

            if (!Files.exists(path)) {

                showError("Folder doesn't exist.");

                return;

            }

            if (!Files.isDirectory(path)) {

                showError("Selected path is not a folder.");

                return;

            }

            try {

                statusLabel.setText("Organizing...");

                organizer.organize(Path.of(folder));

                statusLabel.setText("Finished!");

            }

            catch (Exception ex) {

                statusLabel.setText("Error!");

                ex.printStackTrace();

            }

        });

        undoButton.setOnAction(event -> {            try {

            statusLabel.setText("Undo...");

            organizer.undo();

            statusLabel.setText("Undo completed!");

        } catch (Exception ex) {

            statusLabel.setText("Undo failed!");

            ex.printStackTrace();

        }

        });

        Scene scene = new Scene(root, 500, 220);

        stage.setScene(scene);

        stage.setResizable(false);

        stage.show();

    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");

        alert.setHeaderText("Operation failed");

        alert.setContentText(message);

        alert.showAndWait();

    }
}