package com.example.workout_tracker_2.frontend;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.workout_tracker_2.SpringFXMLLoader;

import javafx.application.Application;
import javafx.application.Platform;
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
    
    private static final double WINDOW_WIDTH = 400;
    private static final double WINDOW_HEIGHT = 700;
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("Starting WorkoutApp...");
            WorkoutApp.primaryStage = primaryStage;

            System.out.println("Loading MainView.fxml...");
            Parent root = springFXMLLoader.load("/frontend/MainView.fxml");
            System.out.println(getClass().getResource("/images/push.png")); 
            
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            //Load CSS
            scene.getStylesheets().add(getClass().getResource("/frontend/styles.css").toExternalForm());

            System.out.println("MainView.fxml loaded successfully.");
            primaryStage.setScene(scene);
            primaryStage.setTitle("Workout Tracker");
            primaryStage.show();
            primaryStage.setResizable(false);
            
        } catch (Exception e) {
            System.err.println("Error loading MainView.fxml:");
            e.printStackTrace();
            Platform.exit();
        }
    }
    

    public static void showMainFrame() throws Exception {
        Parent root = springFXMLLoader.load("/frontend/MainView.fxml");
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Workout Tracker - Main");
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void showWorkoutLogFrame() throws Exception {
        Parent root = springFXMLLoader.load("/frontend/ViewWorkoutLogs.fxml");
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Workout Tracker - Log");
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void showSessionsFrame(Long workoutId) throws Exception {
        System.out.println("----------->Workout ID: " + workoutId);
    	// Load the FXML file
        Parent root = springFXMLLoader.load("/frontend/WorkoutUI.fxml");

        // Get the JavaFX controller and pass the workout ID
        ExerciseUIController controller = springFXMLLoader.getController(ExerciseUIController.class);
        if (controller == null) {
            throw new IllegalStateException("Controller not found for WorkoutUI.fxml");
        }
        System.out.println("----------->Controller: " + controller);

        controller.setWorkoutId(workoutId);
        
        // Set the new scene on the primaryStage
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Log Workout - Workout ID: " + workoutId);
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void showExerciseListFrame() throws Exception {
        Parent root = springFXMLLoader.load("/frontend/ViewExerciseList.fxml");
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Workout Tracker - Exercises");
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    //App can now properly quit without having to force quit
    @Override
    public void stop() throws Exception {
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit(); // Properly exit JavaFX
        System.exit(0); // Terminate the JVM
    }

    public static SpringFXMLLoader getSpringFXMLLoader() {
        return springFXMLLoader;
    }

}