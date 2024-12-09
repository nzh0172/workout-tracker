package com.example.workout_tracker_2.entity;

import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; // Single field for JPA and UI

    @Transient
    private final StringProperty nameProperty = new SimpleStringProperty(); // JavaFX property for binding

    @OneToMany(mappedBy = "workout", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    // Default Constructor
    public Workout() {
        this.nameProperty.addListener((obs, oldVal, newVal) -> this.name = newVal); // Sync changes
    }

    // Parameterized Constructor
    public Workout(String name) {
        this();
        this.name = name;
        this.nameProperty.set(name);
    }

    @PostLoad
    private void syncAfterLoad() {
        this.nameProperty.set(this.name);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameProperty.set(name);
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return name;
    }
}