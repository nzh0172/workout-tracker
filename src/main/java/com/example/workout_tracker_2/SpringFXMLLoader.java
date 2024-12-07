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

    public SpringFXMLLoader(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    public Parent load(String fxmlPath) throws IOException {
        URL resource = getClass().getResource(fxmlPath);
        if (resource == null) {
            throw new IllegalStateException("FXML file not found: " + fxmlPath);
        }
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(springContext::getBean);
        return loader.load();
    }
}