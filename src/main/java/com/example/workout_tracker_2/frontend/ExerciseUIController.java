package com.example.workout_tracker_2.frontend;

import org.springframework.stereotype.Component;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.service.ExerciseService;
import com.example.workout_tracker_2.service.ExerciseSetService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

@Component
public class ExerciseUIController {

    private final ExerciseService exercisesService;
    private final ExerciseSetService exerciseSetService;

    @FXML
    private TextField nameField;

    @FXML
    private TextField repsField;

    @FXML
    private TextField weightField;

    @FXML
    private TableView<Exercise> exercisesTable;

    @FXML
    private TableColumn<Exercise, String> nameColumn;

    @FXML
    private TableView<ExerciseSet> setsTable;

    @FXML
    private TableColumn<ExerciseSet, Integer> repsColumn;

    @FXML
    private TableColumn<ExerciseSet, Double> weightColumn;

    private ObservableList<Exercise> exercises = FXCollections.observableArrayList();
    private ObservableList<ExerciseSet> exerciseSets = FXCollections.observableArrayList();

    public ExerciseUIController(ExerciseService exercisesService, ExerciseSetService exerciseSetService) {
        this.exercisesService = exercisesService;
        this.exerciseSetService = exerciseSetService;
    }

    @FXML
    public void initialize() {
        // Initialize table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        repsColumn.setCellValueFactory(cellData -> cellData.getValue().repsProperty().asObject());
        weightColumn.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());

        // Load exercises
        loadExercises();
        
        // Bind selected exercise to sets table
        exercisesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadExerciseSets(newValue);
            }
        });

        // Populate tables
        exercisesTable.setItems(exercises);
        setsTable.setItems(exerciseSets);
    }

    private void loadExercises() {
        exercises.clear();
        exercises.addAll(exercisesService.getAllExercises());
    }

    private void loadExerciseSets(Exercise exercise) {
        exerciseSets.clear();
        if (exercise != null) {
            exerciseSets.addAll(exercise.getSets());
        }
    }

    @FXML
    public void addExercise() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            showError("Exercise name cannot be empty.");
            return;
        }

        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercisesService.saveExercise(exercise);
        loadExercises();
        clearFields();
    }

    @FXML
    public void addExerciseSet() {
        Exercise selectedExercise = exercisesTable.getSelectionModel().getSelectedItem();
        if (selectedExercise == null) {
            showError("Please select an exercise first.");
            return;
        }

        try {
            int reps = Integer.parseInt(repsField.getText());
            double weight = Double.parseDouble(weightField.getText());

            ExerciseSet set = new ExerciseSet();
            set.setReps(reps);
            set.setWeight(weight);
            set.setExercise(selectedExercise);

            exerciseSetService.saveExerciseSet(set);

            loadExerciseSets(selectedExercise);
            clearFields();
        } catch (NumberFormatException e) {
            showError("Invalid reps or weight value.");
        }
    }

    private void clearFields() {
        nameField.clear();
        repsField.clear();
        weightField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
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