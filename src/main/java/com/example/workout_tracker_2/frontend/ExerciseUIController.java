package com.example.workout_tracker_2.frontend;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.entity.Workout;
import com.example.workout_tracker_2.entity.WorkoutLog;
import com.example.workout_tracker_2.service.ExerciseService;
import com.example.workout_tracker_2.service.ExerciseSetService;
import com.example.workout_tracker_2.service.WorkoutLogService;
import com.example.workout_tracker_2.service.WorkoutService;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ExerciseUIController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExerciseSetService exerciseSetService;
    
    @Autowired
    private WorkoutService workoutService;
    
    @Autowired
    private WorkoutLogService workoutLogService;

    @FXML
    private VBox exerciseList;
    
    @FXML
    private ScrollPane exerciseScrollPane;

    @FXML
    private Button addExerciseButton;

    @FXML
    private Button finishButton, pauseButton, quitButton, resumeButton;
    
    private Long workoutId; // The ID of the current workout session
    
    
    private TimerUIController timerController;
    @FXML
    private HBox timerContainer; // root node of TimerUI.fxml
    
    @FXML
    private Label workoutLabel, pauseLabel;

    @FXML
    private Pane overlay;

    private boolean isPaused = false;

    private void togglePauseOverlay() {
        isPaused = !isPaused; // Toggle pause state
        overlay.setVisible(isPaused); // Show or hide the overlay
        
        if (isPaused) {
            pauseButton.setText("▶"); // Change to "Resume"
            timerController.pauseTimer(); // Pause the timer
        } else {
            pauseButton.setText("⏸"); // Change to "Pause"
            timerController.startTimer(); // Resume the timer
        }
    }
    public void initialize() {
        // Handle "Add Exercise" button
        addExerciseButton.setOnAction(event -> addExerciseCard("New Exercise"));

        // Timer initialization
        // Look up the TimerUIController from the included FXML
        timerController = (TimerUIController) timerContainer.getProperties().get("timerController");

        pauseButton.setOnAction(event -> {
            if (timerController != null) {
                togglePauseOverlay(); // Pause or resume workout
            } else {
                System.err.println("TimerController is null!");
            }
        });

        // Timer initialization
        if (timerController != null) {
            timerController.startTimer(); // Start the timer automatically
        } else {
            System.err.println("TimerController is null!");
        }
        
        // Handle "Save" button
        finishButton.setOnAction(event -> finishWorkout());
    }
    


    // Set the workout ID and load the exercises for that workout
    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
        System.out.println("Workout ID set to: " + workoutId);
        
        //Load today's session or fallback to loading all exercise (for other days)
        loadWorkoutSession();
    }
    
 // Load the workout session for today
    public void loadWorkoutSession() {
        // Fetch the latest workout log for today
        Optional<WorkoutLog> latestWorkoutLog = workoutLogService.findLatestByWorkoutAndDate(workoutId, LocalDate.now());

        // Clear the current UI
        exerciseList.getChildren().clear();

        if (latestWorkoutLog.isPresent()) {
            System.out.println("Loading the latest workout log for today.");
            WorkoutLog workoutLog = latestWorkoutLog.get();

            // Loop through exercises in the workout log and update UI
            for (Exercise exercise : workoutLog.getWorkout().getExercises()) {
                addExerciseCard(exercise, workoutLog);
            }
        } else {
            System.out.println("No workout log found for today. Starting a new session.");
        }
    }

 // Load exercises and their sets for the given workout ID (generic method) - no date
    private void loadExercisesForWorkout(Long workoutId) {
        exerciseList.getChildren().clear(); // Clear the current list
        List<Exercise> exercises = exerciseService.getExercisesByWorkoutId(workoutId); // Fetch exercises for the workout
        for (Exercise exercise : exercises) {
            addExerciseCard(exercise, null); // No associated workout log
        }
    }

 // Add an exercise card for an existing exercise from the database
    private void addExerciseCard(Exercise exercise, WorkoutLog workoutLog) {
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

        List<ExerciseSet> sets;
        if (workoutLog != null) {
            // Load sets for the specific workout log
            sets = exerciseSetService.getSetsByExerciseAndWorkoutLog(exercise, workoutLog);
        } else {
            // Load all sets for the exercise (optional fallback)
            sets = exerciseSetService.getSetsByExerciseId(exercise.getId());
        }

        int row = 0;
        for (ExerciseSet set : sets) {
            addSetRow(setGrid, row++, set.getWeight(), set.getReps(), sets);
        }

        exerciseCard.getChildren().add(setGrid);

        // Add "+ SETS" button
        Button addSetsButton = new Button("+ SETS");
        addSetsButton.getStyleClass().add("add-reps-button");
        addSetsButton.setOnAction(event -> addSetRow(setGrid, setGrid.getRowCount(), null, null, sets));
        exerciseCard.getChildren().add(addSetsButton);

        exerciseList.getChildren().add(exerciseCard);
    }

    private void addExerciseCard(String exerciseName) {
        // Check if an exercise with the same name already exists in the UI
        boolean existsInUI = exerciseList.getChildren().stream()
                .map(node -> (VBox) node)
                .map(vbox -> (TextField) vbox.getChildren().get(0))
                .anyMatch(textField -> textField.getText().equalsIgnoreCase(exerciseName));

        if (existsInUI) {
            System.out.println("Exercise with name '" + exerciseName + "' already exists in the UI.");
            return; // Prevent adding duplicate exercises
        }
        
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

    @FXML
    private void finishWorkout() {
        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Finish Workout");
        confirmationAlert.setHeaderText("Are you sure you want to finish the workout?");
        confirmationAlert.setContentText("Your progress will be saved.");

        // Wait for user response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed, save the workout
            saveWorkout();
            
          //Reset timer for next session
            timerController.resetTimer();

            // Redirect to the main UI
            goToMainUI();
        } else {
            // User cancelled, do nothing
            System.out.println("User canceled finishing the workout.");
        }
    }

    private void saveWorkout() {
        if (timerController == null) {
            System.err.println("TimerController is null! Elapsed time is not recorded.");
            return;
        }

        int elapsedTimeInSeconds = timerController.getElapsedTime();
        System.out.println("Elapsed Time: " + elapsedTimeInSeconds + " seconds");

        // Fetch the Workout entity
        Workout workout = workoutService.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout ID: " + workoutId));

        // Create and save the WorkoutLog
        final WorkoutLog workoutLog = new WorkoutLog();
        workoutLog.setWorkout(workout);
        workoutLog.setDate(LocalDate.now());
        workoutLog.setDuration(elapsedTimeInSeconds);
        workoutLogService.saveLog(workoutLog); // Save the WorkoutLog first

        // Iterate through the exercises and their sets
        exerciseList.getChildren().forEach(node -> {
            VBox exerciseCard = (VBox) node;
            TextField exerciseTitle = (TextField) exerciseCard.getChildren().get(0);
            String exerciseName = exerciseTitle.getText();

            // Check if the exercise already exists for this workout
            Exercise exercise = exerciseService.findByNameAndWorkout(exerciseName, workout)
                    .orElseGet(() -> {
                        // Create a new Exercise if not found
                        Exercise newExercise = new Exercise();
                        newExercise.setName(exerciseName);
                        newExercise.setWorkout(workout);
                        return exerciseService.save(newExercise);
                    });

            GridPane setGrid = (GridPane) exerciseCard.getChildren().get(1);
            for (int i = 0; i < setGrid.getRowCount(); i++) {
                TextField kgField = (TextField) setGrid.getChildren().get(i * 3); // Adjust based on structure
                TextField repsField = (TextField) setGrid.getChildren().get(i * 3 + 1);

                Double weight = null;
                Integer reps = null;

                try {
                    weight = kgField.getText().isEmpty() ? null : Double.valueOf(kgField.getText());
                    reps = repsField.getText().isEmpty() ? null : Integer.valueOf(repsField.getText());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid input for weight or reps: " + e.getMessage());
                    continue; // Skip this set
                }

                ExerciseSet set = new ExerciseSet();
                set.setWeight(weight != null ? weight : 0.0); // Default value to avoid nulls
                set.setReps(reps != null ? reps : 0); // Default value to avoid nulls
                set.setExercise(exercise);
                set.setWorkoutLog(workoutLog); // Link the set to the saved WorkoutLog
                exerciseSetService.saveExerciseSet(set);
            }
        });
        
        System.out.println("Workout session saved!");
    }
    
    // Optionally expose methods to interact with the timer
    public void startTimer() {
        if (timerController != null) {
            timerController.startTimer();
        }
    }

    public void pauseTimer() {
        if (timerController != null) {
            timerController.pauseTimer();
        }
    }
    
    @FXML
    public void goToMainUI() {
        try {
            WorkoutApp.showMainFrame(); // Navigate back to the Main Frame
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Overlay menu
    @FXML
    private void resumeWorkout() {
        if (isPaused) {
            togglePauseOverlay(); // Resume workout
        }
    }

    @FXML
    private void quitWorkout() {
        // Show a confirmation dialog before quitting
        Alert quitConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        quitConfirmation.setTitle("Quit Workout");
        quitConfirmation.setHeaderText("Are you sure you want to quit?");
        quitConfirmation.setContentText("Progress will not be saved if you quit.");

        Optional<ButtonType> result = quitConfirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            goToMainUI(); // Navigate back to the main UI
            timerController.resetTimer();//Reset timer for next session
        }
    }
}