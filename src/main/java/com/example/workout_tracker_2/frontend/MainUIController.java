package com.example.workout_tracker_2.frontend;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

//actionlistener
@Component
public class MainUIController {
	
    @FXML
    private Button pushButton;

    @FXML
    private Button pullButton;

    @FXML
    private Button legsButton;

    @FXML
    public void initialize() {
        // Pass workout IDs to the handler
        pushButton.setOnAction(event -> goToWorkoutUI(2L)); // Pass ID for "Push"
        pullButton.setOnAction(event -> goToWorkoutUI(3L)); // Pass ID for "Pull"
        legsButton.setOnAction(event -> goToWorkoutUI(1L)); // Pass ID for "Legs"
    }

    private void goToWorkoutUI(Long workoutId) {
        try {
            WorkoutApp.showSessionsFrame(workoutId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

    @FXML
    public void goToWorkoutLogUI() {
        try {
            WorkoutApp.showWorkoutLogFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void goToExercisesUI() {
        try {
            WorkoutApp.showExerciseListFrame();
        } catch (Exception e) {
            e.printStackTrace();
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
}

