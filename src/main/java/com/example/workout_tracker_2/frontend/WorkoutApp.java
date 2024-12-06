package com.example.workout_tracker_2.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WorkoutApp extends Application {

    private static Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	WorkoutApp.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/MainView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Workout Tracker");
        primaryStage.show();
    }
    

    public static void showMainFrame() throws Exception {
        Parent root = FXMLLoader.load(WorkoutApp.class.getResource("/frontend/MainView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker - Main");
        primaryStage.show();
    }

    public static void showWorkoutLogFrame() throws Exception {
        Parent root = FXMLLoader.load(WorkoutApp.class.getResource("/frontend/ViewWorkoutLogs.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker - Log");
        primaryStage.show();
    }

    public static void showSessionsFrame() throws Exception {
        Parent root = FXMLLoader.load(WorkoutApp.class.getResource("/frontend/SessionsView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker - Sessions");
        primaryStage.show();
    }

    public static void showExercisesFrame() throws Exception {
        Parent root = FXMLLoader.load(WorkoutApp.class.getResource("/frontend/ExercisesView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker - Exercises");
        primaryStage.show();
    }
	


    public static void main(String[] args) {
        launch(args);
    }



}

