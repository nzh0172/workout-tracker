package com.example.workout_tracker_2.frontend;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.entity.WorkoutLog;
import com.example.workout_tracker_2.service.WorkoutLogService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        // Outer HBox to separate the left and right sections
        HBox logCard = new HBox(20);
        logCard.setStyle("-fx-padding: 15; -fx-background-color: #f5f5f5; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8;");
        logCard.setPrefWidth(700);

        // LEFT SIDE: Date, Workout Name, Timer
        VBox leftSection = new VBox(15);
        leftSection.setStyle("-fx-padding: 10; -fx-alignment: TOP_LEFT;");

        // Date Label
        Label dateLabel = new Label(workoutLog.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Workout Name Label
        Label workoutNameLabel = new Label(workoutLog.getWorkout().getName());
        workoutNameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        workoutNameLabel.setWrapText(true);

        // Duration Label with Stopwatch Icon
        HBox durationBox = new HBox(5);
        durationBox.setStyle("-fx-alignment: CENTER_LEFT;");
        Label stopwatchIcon = new Label("\u23F1"); // Unicode stopwatch icon
        stopwatchIcon.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
        Label durationLabel = new Label(formatDuration(workoutLog.getDuration()));
        durationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
        durationBox.getChildren().addAll(stopwatchIcon, durationLabel);

        // Add components to the left section
        leftSection.getChildren().addAll(dateLabel, workoutNameLabel, durationBox);

        // RIGHT SIDE: Exercises and Sets
        VBox rightSection = new VBox(10);
        rightSection.setStyle("-fx-padding: 10");
        HBox.setHgrow(rightSection, Priority.ALWAYS); // Allow right section to expand dynamically

        
        for (Exercise exercise : workoutLog.getWorkout().getExercises()) {
            VBox exerciseVBox = new VBox(5);
            exerciseVBox.setStyle("-fx-padding: 10; -fx-background-color: #e8f5e9; -fx-border-color: #c8e6c9; -fx-border-radius: 5;");

            // Exercise Name
            Label exerciseLabel = new Label(exercise.getName());
            exerciseLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            exerciseVBox.getChildren().add(exerciseLabel);

            // Filter and display sets for the current workout log
            List<ExerciseSet> filteredSets = exercise.getSets().stream()
                .filter(set -> set.getWorkoutLog() != null && set.getWorkoutLog().getId().equals(workoutLog.getId())) // Ensure sets belong to this log
                .toList();

            for (ExerciseSet set : filteredSets) {
                Label setLabel = new Label(set.getReps() + " x " + set.getWeight() + "kg");
                setLabel.setStyle("-fx-font-size: 14px;");
                exerciseVBox.getChildren().add(setLabel);
            }

            // Add each exercise VBox to the right section
            rightSection.getChildren().add(exerciseVBox);
        }

        // Add both sections to the log card
        logCard.getChildren().addAll(leftSection, rightSection);

        // Add the log card to the main container
        logContainer.getChildren().add(logCard);
    }

    // Helper to format duration
    private String formatDuration(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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