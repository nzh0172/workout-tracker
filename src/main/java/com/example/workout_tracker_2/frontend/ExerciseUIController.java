package com.example.workout_tracker_2.frontend;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.entity.Workout;
import com.example.workout_tracker_2.service.ExerciseService;
import com.example.workout_tracker_2.service.ExerciseSetService;
import com.example.workout_tracker_2.service.WorkoutService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExerciseUIController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExerciseSetService exerciseSetService;
    
    @Autowired
    private WorkoutService workoutService;

    @FXML
    private VBox exerciseList;
    
    @FXML
    private ScrollPane exerciseScrollPane;

    @FXML
    private Button addExerciseButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Label timerLabel;

    private Long workoutId; // The ID of the current workout session
    
    @FXML
    private Label workoutLabel;

    public void initialize() {
        // Handle "Add Exercise" button
        addExerciseButton.setOnAction(event -> addExerciseCard("New Exercise"));

        // Handle "Save" button
        saveButton.setOnAction(event -> saveWorkout());
    }

    // Set the workout ID and load the exercises for that workout
    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
        System.out.println("Workout ID set to: " + workoutId);
        loadExercisesForWorkout(workoutId);
    }
    

    // Load exercises and their sets for the given workout ID
    private void loadExercisesForWorkout(Long workoutId) {
        exerciseList.getChildren().clear(); // Clear the current list
        List<Exercise> exercises = exerciseService.getExercisesByWorkoutId(workoutId); // Fetch exercises for the workout
        for (Exercise exercise : exercises) {
            addExerciseCard(exercise); // Add exercises dynamically
        }
    }

 // Add an exercise card for an existing exercise from the database
    private void addExerciseCard(Exercise exercise) {
        VBox exerciseCard = new VBox(5);
        exerciseCard.getStyleClass().add("exercise-card");

        // Add exercise name
        TextField exerciseTitle = new TextField(exercise.getName());
        exerciseTitle.getStyleClass().add("exercise-title");
        exerciseCard.getChildren().add(exerciseTitle);

        // Add input fields for existing sets
        GridPane setGrid = new GridPane();
        setGrid.setHgap(10);
        setGrid.setVgap(5);
        List<ExerciseSet> sets = exerciseSetService.getSetsByExerciseId(exercise.getId());
        int row = 0;
        for (ExerciseSet set : sets) {
            addSetRow(setGrid, row++, set.getWeight(), set.getReps(), sets);
        }
        exerciseCard.getChildren().add(setGrid);

        // Add "+ REPS" button
        Button addRepsButton = new Button("+ REPS");
        addRepsButton.getStyleClass().add("add-reps-button");
        addRepsButton.setOnAction(event -> addSetRow(setGrid, setGrid.getRowCount(), null, null, sets));
        exerciseCard.getChildren().add(addRepsButton);

        exerciseList.getChildren().add(exerciseCard);
    }

    // Add a new exercise card dynamically for a new exercise
    private void addExerciseCard(String exerciseName) {
        VBox exerciseCard = new VBox(5);
        exerciseCard.getStyleClass().add("exercise-card");

        // Add exercise name
        TextField exerciseTitle = new TextField(exerciseName);
        exerciseTitle.getStyleClass().add("exercise-title");
        exerciseCard.getChildren().add(exerciseTitle);

        // Add input fields for the first set
        GridPane setGrid = new GridPane();
        setGrid.setHgap(10);
        setGrid.setVgap(5);
        List<ExerciseSet> sets = new ArrayList<>();
        addSetRow(setGrid, 0, null, null, sets);
        exerciseCard.getChildren().add(setGrid);

        // Add "+ REPS" button
        Button addRepsButton = new Button("+ REPS");
        addRepsButton.getStyleClass().add("add-reps-button");
        addRepsButton.setOnAction(event -> addSetRow(setGrid, setGrid.getRowCount(), null, null, sets));
        exerciseCard.getChildren().add(addRepsButton);

        exerciseList.getChildren().add(exerciseCard);
    }


 // Add a new row to the GridPane for a set
    private void addSetRow(GridPane setGrid, int row, Double weight, Integer reps, List<ExerciseSet> sets) {
        TextField kgField = new TextField(weight != null ? weight.toString() : "");
        kgField.setPromptText("kg");
        kgField.setPrefWidth(50);
        setGrid.add(kgField, 0, row);

        TextField repsField = new TextField(reps != null ? reps.toString() : "");
        repsField.setPromptText("reps");
        repsField.setPrefWidth(50);
        setGrid.add(repsField, 1, row);

        Button deleteButton = new Button("✖");
        deleteButton.getStyleClass().add("delete-set-button");
        setGrid.add(deleteButton, 2, row);

        deleteButton.setOnAction(event -> {
            // Remove the children in the specified row
            setGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row);

            // Remove the ExerciseSet from the list if it exists
            if (row < sets.size()) {
                sets.remove(row);
            }

            // Reassign row indices for remaining children
            for (int i = 0; i < setGrid.getChildren().size(); i++) {
                int currentRow = GridPane.getRowIndex(setGrid.getChildren().get(i));
                if (currentRow > row) {
                    GridPane.setRowIndex(setGrid.getChildren().get(i), currentRow - 1);
                }
            }
        });
    }
    
 // Reindex the rows in the GridPane after a deletion
    private void reindexRows(GridPane setGrid) {
        int rowCount = 0;
        for (var child : setGrid.getChildren()) {
            Integer currentRowIndex = GridPane.getRowIndex(child);
            if (currentRowIndex != null) {
                GridPane.setRowIndex(child, rowCount / 3); // 3 columns (kgField, repsField, deleteButton)
            }
        }
    }


    // Save workout session to the database
    private void saveWorkout() {
        exerciseList.getChildren().forEach(node -> {
            VBox exerciseCard = (VBox) node;
            TextField exerciseTitle = (TextField) exerciseCard.getChildren().get(0);
            String exerciseName = exerciseTitle.getText();

            Exercise exercise = new Exercise();
            exercise.setName(exerciseName);

            // Fetch the Workout entity using workoutId
            Workout workout = workoutService.findById(workoutId)
                                            .orElseThrow(() -> new IllegalArgumentException("Invalid workout ID: " + workoutId));

            // Set the Workout entity
            exercise.setWorkout(workout);
            exerciseService.saveExercise(exercise);

            // Save the sets
            GridPane setGrid = (GridPane) exerciseCard.getChildren().get(1);
            for (int i = 0; i < setGrid.getRowCount(); i++) {
                TextField kgField = (TextField) setGrid.getChildren().get(i * 2);
                TextField repsField = (TextField) setGrid.getChildren().get(i * 2 + 1);

                Double weight = Double.valueOf(kgField.getText().isEmpty() ? "0" : kgField.getText());
                Integer reps = Integer.valueOf(repsField.getText().isEmpty() ? "0" : repsField.getText());

                ExerciseSet set = new ExerciseSet();
                set.setWeight(weight);
                set.setReps(reps);
                set.setExercise(exercise); // Link the set to the exercise
                exerciseSetService.saveExerciseSet(set);
            }
        });

        System.out.println("Workout session saved!");
    }
}