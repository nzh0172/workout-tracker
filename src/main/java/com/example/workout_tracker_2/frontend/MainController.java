package com.example.workout_tracker_2.frontend;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;

//actionlistener
@Component
public class MainController {

    @FXML
    public void goToWorkoutLogUI() {
        try {
            WorkoutApp.showWorkoutLogFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSessionsUI() {
        try {
            WorkoutApp.showSessionsFrame();
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

