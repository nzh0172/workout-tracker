package com.example.workout_tracker_2.frontend;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class TimerUIController {

    @FXML
    private Label timerLabel;

    @FXML
    private Button startPauseButton;

    @FXML
    private Button resetButton;

    private Timeline timeline;
    private IntegerProperty elapsedTimeProperty = new SimpleIntegerProperty(0);

    public void initialize() {
        timerLabel.textProperty().bind(Bindings.createStringBinding(
                () -> formatElapsedTime(elapsedTimeProperty.get()),
                elapsedTimeProperty
        ));

        // Register this controller in the root node's properties
        if (timerLabel != null) {
            timerLabel.getParent().getProperties().put("timerController", this);
        }
        
        //Set button actions
        startPauseButton.setText("Pause");
        startPauseButton.setOnAction(event -> handleStartPause());
        
        resetButton.setOnAction(event -> resetTimer());

        // Initialize the timeline
        setupTimeline();
    }
    
    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            elapsedTimeProperty.set(elapsedTimeProperty.get() + 1);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    
    private void handleStartPause() {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            // If the timer is running, pause it
            pauseTimer();
            startPauseButton.setText("Start");
        } else {
            // If the timer is not running, start it
            startTimer();
            startPauseButton.setText("Pause");
        }
    }

    public void startTimer() {
        if (timeline != null && timeline.getStatus() != Animation.Status.RUNNING) {
            timeline.play();
        }
    }

    public void pauseTimer() {
        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause();
        }
    }

    private void resetTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        elapsedTimeProperty.set(0);
        startPauseButton.setText("Start");
    }

    private String formatElapsedTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}