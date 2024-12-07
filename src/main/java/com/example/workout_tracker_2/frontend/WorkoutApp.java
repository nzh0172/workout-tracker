package com.example.workout_tracker_2.frontend;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.workout_tracker_2.SpringFXMLLoader;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.workout_tracker_2")
public class WorkoutApp extends Application {

    private static Stage primaryStage;
    private ConfigurableApplicationContext springContext;
    private static SpringFXMLLoader springFXMLLoader;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(WorkoutApp.class).web(WebApplicationType.NONE).run();
        springFXMLLoader = new SpringFXMLLoader(springContext);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WorkoutApp.primaryStage = primaryStage;
        Parent root = springFXMLLoader.load("/frontend/MainView.fxml");
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker");
        primaryStage.show();
    }

    public static void showMainFrame() throws Exception {
        Parent root = springFXMLLoader.load("/frontend/MainView.fxml");
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker - Main");
        primaryStage.show();
    }

    public static void showWorkoutLogFrame() throws Exception {
        Parent root = springFXMLLoader.load("/frontend/ViewWorkoutLogs.fxml");
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker - Log");
        primaryStage.show();
    }

    public static void showSessionsFrame() throws Exception {
        Parent root = springFXMLLoader.load("/frontend/ViewSessions.fxml");
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker - Sessions");
        primaryStage.show();
    }

    public static void showExerciseListFrame() throws Exception {
        Parent root = springFXMLLoader.load("/frontend/ViewExerciseList.fxml");
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Workout Tracker - Exercises");
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }

    public static SpringFXMLLoader getSpringFXMLLoader() {
        return springFXMLLoader;
    }

}