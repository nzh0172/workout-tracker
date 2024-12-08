package com.example.workout_tracker_2.frontend;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.service.ExerciseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExerciseManagementUIController {

    @FXML
    private TableView<Exercise> exerciseTable;

    @FXML
    private TableColumn<Exercise, String> nameColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @Autowired
    private ExerciseService exerciseService;

    private final ObservableList<Exercise> exercises = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Load exercises from the database
        loadExercises();
        
        //exercises.forEach(e -> System.out.println("Exercise: " + e.getNameValue()));

        // Add button event
        addButton.setOnAction(event -> addExercise());

        // Delete button event
        deleteButton.setOnAction(event -> deleteExercise());
    }

    private void loadExercises() {
        exercises.clear();
        List<Exercise> exerciseList = exerciseService.getAllExercises();
        if (exerciseList != null) {
            exercises.addAll(exerciseList);
        }
        exerciseTable.setItems(exercises);
        System.out.println("Fetched exercises: " + exerciseList);
    }

    private void addExercise() {
        // Example logic to add a new exercise
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Exercise");
        dialog.setHeaderText("Enter exercise name:");
        dialog.setContentText("Name:");

        dialog.showAndWait().ifPresent(name -> {
            Exercise newExercise = new Exercise();
            newExercise.setName(name);
            exerciseService.saveExercise(newExercise);
            loadExercises();
        });
    }

    private void deleteExercise() {
        Exercise selectedExercise = exerciseTable.getSelectionModel().getSelectedItem();
        if (selectedExercise != null) {
            exerciseService.deleteExercise(selectedExercise.getId());
            loadExercises();
        } else {
            showAlert("No Selection", "No exercise selected to delete.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    public void goToMainUI() {
        try {
            WorkoutApp.showMainFrame(); // Navigate back to the Main Frame
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}