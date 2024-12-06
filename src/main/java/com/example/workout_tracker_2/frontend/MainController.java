package com.example.workout_tracker_2.frontend;

import javafx.fxml.FXML;

//actionlistener
public class MainController {

    @FXML
    public void goToWorkoutLog() {
        try {
            WorkoutApp.showWorkoutLogFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSessions() {
        try {
            WorkoutApp.showSessionsFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToExercises() {
        try {
            WorkoutApp.showExercisesFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void goToMain() {
        try {
            WorkoutApp.showMainFrame(); // Navigate back to the Main Frame
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

