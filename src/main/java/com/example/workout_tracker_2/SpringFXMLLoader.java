package com.example.workout_tracker_2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class SpringFXMLLoader {

    private final ApplicationContext springContext;
    private FXMLLoader currentLoader; // Store the current loader instance

    public SpringFXMLLoader(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    public Parent load(String fxmlPath) throws IOException {
        URL resource = getClass().getResource(fxmlPath);
        if (resource == null) {
            throw new IllegalStateException("FXML file not found: " + fxmlPath);
        }
        // Create a new loader and store it
        currentLoader = new FXMLLoader(resource);
        currentLoader.setControllerFactory(springContext::getBean);
        return currentLoader.load();
    }

    public <T> T getController(Class<T> controllerClass) {
        if (currentLoader == null) {
            throw new IllegalStateException("No FXML file has been loaded yet.");
        }
        // Retrieve and return the controller instance
        return controllerClass.cast(currentLoader.getController());
    }
}