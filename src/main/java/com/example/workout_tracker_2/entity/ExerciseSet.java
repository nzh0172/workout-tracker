package com.example.workout_tracker_2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

@Entity
@Table(name = "exercise_set")
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    @JsonBackReference
    private Exercise exercise;
    
    @ManyToOne
    @JoinColumn(name = "workout_log_id", nullable = true) // Nullable for backward compatibility
    @JsonBackReference
    private WorkoutLog workoutLog;


    @Column(name = "reps", nullable = false)
    private int reps; // Single field for JPA and UI

    @Column(name = "weight", nullable = false)
    private double weight; // Single field for JPA and UI

    @Transient
    @JsonIgnore // This value is not recorded in JSON
    private final IntegerProperty repsProperty = new SimpleIntegerProperty(); // For JavaFX binding

    @Transient
    @JsonIgnore // This value is not recorded in JSON
    private final DoubleProperty weightProperty = new SimpleDoubleProperty(); // For JavaFX binding

    // Default Constructor
    public ExerciseSet() {
        this.repsProperty.addListener((obs, oldVal, newVal) -> this.reps = newVal.intValue()); // Sync JavaFX property
        this.weightProperty.addListener((obs, oldVal, newVal) -> this.weight = newVal.doubleValue()); // Sync JavaFX property
    }

    // Parameterized Constructor for Database
    public ExerciseSet(Long id, int reps, double weight, Exercise exercise) {
        this();
        this.id = id;
        this.reps = reps;
        this.weight = weight;
        this.exercise = exercise;
        this.repsProperty.set(reps); // Sync JavaFX property
        this.weightProperty.set(weight); // Sync JavaFX property
    }

    // Parameterized Constructor for UI
    public ExerciseSet(int reps, double weight) {
        this();
        this.reps = reps;
        this.weight = weight;
        this.repsProperty.set(reps); // Sync JavaFX property
        this.weightProperty.set(weight); // Sync JavaFX property
    }

    @PostLoad
    private void syncAfterLoad() {
        this.repsProperty.set(this.reps); // Synchronize JavaFX property with JPA field
        this.weightProperty.set(this.weight); // Synchronize JavaFX property with JPA field
    }

    // Getters and Setters for JPA
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
        this.repsProperty.set(reps); // Sync JavaFX property
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        this.weightProperty.set(weight); // Sync JavaFX property
    }

    // Getters and Setters for JavaFX properties
    public IntegerProperty repsProperty() {
        return repsProperty;
    }

    public DoubleProperty weightProperty() {
        return weightProperty;
    }
    
    public WorkoutLog getWorkoutLog() {
        return workoutLog;
    }

    public void setWorkoutLog(WorkoutLog workoutLog) {
        this.workoutLog = workoutLog;
    }

    @Override
    public String toString() {
        return "ExerciseSet{id=" + id + ", reps=" + reps + ", weight=" + weight + "}";
    }
}