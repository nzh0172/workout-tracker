package com.example.workout_tracker_2.frontend;

import javafx.fxml.FXML;

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
}