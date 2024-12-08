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
    private boolean isRunning = false;

    public void initialize() {
        timerLabel.textProperty().bind(Bindings.createStringBinding(
                () -> formatElapsedTime(elapsedTimeProperty.get()),
                elapsedTimeProperty
        ));

        startPauseButton.setOnAction(event -> {
            if (isRunning) {
                pauseTimer();
                startPauseButton.setText("Start");
            } else {
                startTimer();
                startPauseButton.setText("Pause");
            }
            isRunning = !isRunning;
        });

        resetButton.setOnAction(event -> resetTimer());
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> elapsedTimeProperty.set(elapsedTimeProperty.get() + 1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    private void resetTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        elapsedTimeProperty.set(0);
        startPauseButton.setText("Start");
        isRunning = false;
    }

    private String formatElapsedTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}