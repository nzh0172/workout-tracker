package com.example.workout_tracker_2.frontend;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.entity.WorkoutLog;
import com.example.workout_tracker_2.service.WorkoutLogService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class WorkoutLogUIController {

    @Autowired
    private WorkoutLogService workoutLogService;

    @FXML
    private VBox logContainer;

    public void initialize() {
        loadWorkoutLogs();
    }

    /**
     * Load all workout logs into the UI.
     */
    private void loadWorkoutLogs() {
        logContainer.getChildren().clear(); // Clear previous logs

        List<WorkoutLog> workoutLogs = workoutLogService.getAllLogsWithDetails();

        // Group logs by date and add them dynamically
        workoutLogs.stream()
                .sorted((log1, log2) -> log2.getDate().compareTo(log1.getDate())) // Sort by date (descending)
                .forEach(this::addWorkoutLogCard);
    }

    /**
     * Add a card for each workout log.
     */
    private void addWorkoutLogCard(WorkoutLog workoutLog) {
        VBox logCard = new VBox(5);
        logCard.setStyle("-fx-padding: 10; -fx-background-color: #f5f5f5; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Date and workout title
        Label dateLabel = new Label(workoutLog.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        logCard.getChildren().add(dateLabel);

        // Workout name
        Label workoutNameLabel = new Label(workoutLog.getWorkout().getName());
        workoutNameLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        logCard.getChildren().add(workoutNameLabel);

        // Container for exercises
        VBox exerciseContainer = new VBox(5);
        exerciseContainer.setStyle("-fx-padding: 5;");

        // Loop through the exercises for this workout log
        for (Exercise exercise : workoutLog.getWorkout().getExercises()) {
            VBox exerciseVBox = new VBox(5);
            exerciseVBox.setStyle("-fx-padding: 10; -fx-background-color: #e8f5e9; -fx-border-color: #c8e6c9;");

            // Add the exercise name
            Label exerciseLabel = new Label(exercise.getName());
            exerciseLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            exerciseVBox.getChildren().add(exerciseLabel);

            // Filter sets that belong to this specific log
            List<ExerciseSet> filteredSets = exercise.getSets().stream()
                .filter(set -> set.getWorkoutLog() != null) // Ensure WorkoutLog is not null
                .filter(set -> set.getWorkoutLog().getId().equals(workoutLog.getId())) // Only sets tied to this log
                .toList();

            // Display filtered sets in the UI
            for (ExerciseSet set : filteredSets) {
                Label setLabel = new Label(set.getReps() + " x " + set.getWeight() + "kg");
                setLabel.setStyle("-fx-font-size: 15px");

                exerciseVBox.getChildren().add(setLabel); // Add set details under the exercise
            }

            // Add the exerciseVBox (with its name and sets) to the exerciseContainer
            exerciseContainer.getChildren().add(exerciseVBox);
        }

        // Add the exerciseContainer to the logCard
        logCard.getChildren().add(exerciseContainer);

        // Add the logCard to the main container
        logContainer.getChildren().add(logCard);
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