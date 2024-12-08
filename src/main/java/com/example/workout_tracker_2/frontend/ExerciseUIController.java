package com.example.workout_tracker_2.frontend;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.entity.Workout;
import com.example.workout_tracker_2.service.ExerciseService;
import com.example.workout_tracker_2.service.ExerciseSetService;
import com.example.workout_tracker_2.service.WorkoutService;

import javafx.fxml.FXML;
import javafx.scene.Node;
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


    private void addSetRow(GridPane setGrid, int row, Double weight, Integer reps, List<ExerciseSet> sets) {
        ExerciseSet currentSet = (row < sets.size()) ? sets.get(row) : new ExerciseSet();
        if (row >= sets.size()) {
            sets.add(currentSet); // Add the set to the list if it's new
        }

        // Weight Field
        String weightText = (weight == null) ? "" : (weight % 1 == 0 ? String.valueOf(weight.intValue()) : weight.toString());
        TextField kgField = new TextField(weightText);
        kgField.setPromptText("kg");
        kgField.setPrefWidth(50);
        kgField.textProperty().addListener((obs, oldVal, newVal) -> {
            currentSet.setWeight(newVal.isEmpty() ? null : Double.valueOf(newVal));
        });
        setGrid.add(kgField, 0, row);

        // Reps Field
        String repsText = (reps == null) ? "" : String.valueOf(reps);
        TextField repsField = new TextField(repsText);
        repsField.setPromptText("reps");
        repsField.setPrefWidth(50);
        repsField.textProperty().addListener((obs, oldVal, newVal) -> {
            currentSet.setReps(newVal.isEmpty() ? null : Integer.valueOf(newVal));
        });
        setGrid.add(repsField, 1, row);

        // Delete Button
        Button deleteButton = new Button("✖");
        deleteButton.getStyleClass().add("delete-set-button");
        setGrid.add(deleteButton, 2, row);

        deleteButton.setOnAction(event -> deleteSet(setGrid, currentSet, sets));
    }
    

    
    private void deleteSet(GridPane setGrid, ExerciseSet setToDelete, List<ExerciseSet> sets) {
        // Prevent deleting the last remaining set
        if (sets.size() <= 1) {
            System.out.println("Cannot delete the last set.");
            return;
        }

        // Remove the set from the list
        int indexToRemove = sets.indexOf(setToDelete);
        if (indexToRemove != -1) {
            sets.remove(indexToRemove);
        }

        // Rebuild the GridPane
        rebuildSetGrid(setGrid, sets);
    }
    
    private void updateSetsFromGrid(GridPane setGrid, List<ExerciseSet> sets) {
        for (Node node : setGrid.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            if (row == null || row >= sets.size()) continue;

            ExerciseSet set = sets.get(row);

            if (GridPane.getColumnIndex(node) == 0 && node instanceof TextField kgField) {
                // Preserve weight as null or parse it as an Integer or Double
                String weightText = kgField.getText().trim();
                set.setWeight(weightText.isEmpty() ? null : Double.valueOf(weightText));
            }

            if (GridPane.getColumnIndex(node) == 1 && node instanceof TextField repsField) {
                // Preserve reps as null or parse it as an Integer
                String repsText = repsField.getText().trim();
                set.setReps(repsText.isEmpty() ? null : Integer.valueOf(repsText));
            }
        }
    }
    
    private void rebuildSetGrid(GridPane setGrid, List<ExerciseSet> sets) {
        setGrid.getChildren().clear(); // Clear all rows

        for (int i = 0; i < sets.size(); i++) {
            ExerciseSet set = sets.get(i);
            addSetRow(setGrid, i, set.getWeight(), set.getReps(), sets); // Rebuild each row
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