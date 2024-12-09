package com.example.workout_tracker_2.frontend;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.Workout;
import com.example.workout_tracker_2.service.ExerciseService;
import com.example.workout_tracker_2.service.WorkoutService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExerciseManagementUIController {

    @FXML
    private TableView<Exercise> exerciseTable;

    @FXML
    private TableColumn<Exercise, String> nameColumn;
    
    @FXML
    private ComboBox<Workout> workoutComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;
    
    @FXML
    private ComboBox<String> filterDropdown;

    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private WorkoutService workoutService;

    private final ObservableList<Exercise> exercises = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Populate the dropdown
        filterDropdown.getItems().addAll("All", "Push", "Pull", "Legs");
        filterDropdown.setValue("All"); // Default selection

        // Add listener to handle filtering
        filterDropdown.setOnAction(event -> filterExercises(filterDropdown.getValue()));
    	
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
        // Create the dialog
        Dialog<Exercise> dialog = new Dialog<>();
        dialog.setTitle("Add Exercise");
        dialog.setHeaderText("Enter exercise details:");

        // Create the dialog content
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label workoutLabel = new Label("Workout:");
        ComboBox<Workout> workoutComboBox = new ComboBox<>();
        workoutComboBox.setItems(FXCollections.observableArrayList(workoutService.getAllWorkouts()));

        // Layout for the dialog
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(workoutLabel, 0, 1);
        gridPane.add(workoutComboBox, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Add buttons to the dialog
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Convert the result when the user clicks "Add"
        dialog.setResultConverter(button -> {
            if (button == addButtonType) {
                String name = nameField.getText().trim();
                Workout selectedWorkout = workoutComboBox.getSelectionModel().getSelectedItem();

                if (name.isEmpty() || selectedWorkout == null) {
                    showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter a valid name and select a workout.");
                    return null;
                }

                // Create and return the new Exercise
                Exercise newExercise = new Exercise();
                newExercise.setName(name);
                newExercise.setWorkout(selectedWorkout);
                return newExercise;
            }
            return null;
        });

        // Show the dialog and get the result
        Optional<Exercise> result = dialog.showAndWait();
        result.ifPresent(exercise -> {
            try {
                // Save the exercise and reload the table
                exerciseService.saveExercise(exercise);
                loadExercises();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Exercise added successfully.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.WARNING, "Error", "Failed to add exercise: " + e.getMessage());
            }
        });
    }

    private void deleteExercise() {
        Exercise selectedExercise = exerciseTable.getSelectionModel().getSelectedItem();

        if (selectedExercise != null) {
            // Show a confirmation dialog
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Delete Confirmation");
            confirmationDialog.setHeaderText("Are you sure you want to delete this exercise?");
            confirmationDialog.setContentText("Exercise: " + selectedExercise.getName());

            // Wait for the user's response
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Call the backend to delete
                        exerciseService.deleteExercise(selectedExercise.getId());

                        // Reload the table
                        loadExercises();

                        showAlert(Alert.AlertType.INFORMATION, "Success", "Exercise deleted successfully.");
                    } catch (Exception e) {
                        showAlert(Alert.AlertType.WARNING, "Error", "Failed to delete exercise: " + e.getMessage());
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "No exercise selected to delete.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Set custom icon based on alert type
        ImageView icon = null;
        try {
        	
            if (type == Alert.AlertType.WARNING) {
                icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/warning-icon.png")));
            } else if (type == Alert.AlertType.INFORMATION) {
                icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/success-icon.png")));
            }

            if (icon != null) {
                icon.setFitHeight(48);
                icon.setFitWidth(48);
                alert.setGraphic(icon);
            }
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + e.getMessage());
        }

        alert.showAndWait();
    }
    
    private void filterExercises(String workoutName) {
        List<Exercise> allExercises = exerciseService.getAllExercises();

        // Filter exercises by workout name
        List<Exercise> filteredExercises = "All".equals(workoutName)
            ? allExercises
            : allExercises.stream()
                          .filter(exercise -> exercise.getWorkout().getName().equalsIgnoreCase(workoutName))
                          .toList();

        // Update the table
        exerciseTable.getItems().setAll(filteredExercises);
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