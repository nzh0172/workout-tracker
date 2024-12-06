package com.example.workout_tracker_2.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.web.client.RestTemplate;

public class WorkoutAppController {

    @FXML
    private TextField workoutNameField;

    @FXML
    private TextField workoutDateField;

    @FXML
    private ListView<String> workoutList;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8080/workouts";

    @FXML
    public void addWorkout() {
        String name = workoutNameField.getText();
        String date = workoutDateField.getText();

        if (!name.isEmpty() && !date.isEmpty()) {
            Workout workout = new Workout(name, date);
            restTemplate.postForObject(BASE_URL, workout, Workout.class);
            workoutNameField.clear();
            workoutDateField.clear();
            loadWorkouts(); // Refresh the list
        } else {
            showAlert("Validation Error", "Both fields are required.");
        }
    }

    @FXML
    public void loadWorkouts() {
        workoutList.getItems().clear();
        Workout[] workouts = restTemplate.getForObject(BASE_URL, Workout[].class);

        if (workouts != null) {
            for (Workout workout : workouts) {
                workoutList.getItems().add(workout.getName() + " (" + workout.getDate() + ")");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class for Workout
    public static class Workout {
        private String name;
        private String date;

        public Workout(String name, String date) {
            this.name = name;
            this.date = date;
        }

        public Workout() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}