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
        Label dateLabel = new Label("Date: " + workoutLog.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        logCard.getChildren().add(dateLabel);

        // Workout name
        Label workoutNameLabel = new Label("Workout: " + workoutLog.getWorkout().getName());
        workoutNameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        logCard.getChildren().add(workoutNameLabel);

        // Exercises and sets
        VBox exerciseContainer = new VBox(5);
        exerciseContainer.setStyle("-fx-padding: 5;");

        for (Exercise exercise : workoutLog.getWorkout().getExercises()) {
            VBox exerciseBox = new VBox(5);
            exerciseBox.setStyle("-fx-padding: 5; -fx-background-color: #e8f5e9; -fx-border-color: #8bc34a; -fx-border-radius: 5;");

            // Exercise name
            Label exerciseNameLabel = new Label(exercise.getName());
            exerciseNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            exerciseBox.getChildren().add(exerciseNameLabel);

            // Sets
            for (ExerciseSet set : exercise.getSets()) {
                Label setLabel = new Label(set.getReps() + " x " + set.getWeight() + "kg");
                setLabel.setStyle("-fx-font-size: 16px;");
                exerciseBox.getChildren().add(setLabel);
            }

            exerciseContainer.getChildren().add(exerciseBox);
        }

        logCard.getChildren().add(exerciseContainer);

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