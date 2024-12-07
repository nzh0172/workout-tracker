package com.example.workout_tracker_2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    //Data types for JPA/Spring Boot
    @Column(nullable = false)
    private int repsValue; // Used for JPA

    @Column(nullable = false)
    private double weightValue; // Used for JPA

    //Data types for JavaFX
    @Transient
    private final IntegerProperty reps = new SimpleIntegerProperty(); // Used for JavaFX binding

    @Transient
    private final DoubleProperty weight = new SimpleDoubleProperty(); // Used for JavaFX binding

    // Default Constructor
    public ExerciseSet() {
    }

    // Parameterized Constructor for Database
    public ExerciseSet(Long id, int reps, double weight, Exercise exercise) {
        this.id = id;
        this.repsValue = reps;
        this.weightValue = weight;
        this.exercise = exercise;
        this.reps.set(reps); // Sync JavaFX property
        this.weight.set(weight); // Sync JavaFX property
    }

    // Parameterized Constructor for UI
    public ExerciseSet(int reps, double weight) {
        this.reps.set(reps); // Sync JavaFX property
        this.weight.set(weight); // Sync JavaFX property
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

    public int getRepsValue() {
        return repsValue;
    }

    public void setRepsValue(int reps) {
        this.repsValue = reps;
        this.reps.set(reps); // Sync JavaFX property
    }

    public double getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(double weight) {
        this.weightValue = weight;
        this.weight.set(weight); // Sync JavaFX property
    }

    // Getters and Setters for JavaFX properties
    public IntegerProperty repsProperty() {
        return reps;
    }

    public int getReps() {
        return reps.get();
    }

    public void setReps(int reps) {
        this.reps.set(reps);
        this.repsValue = reps; // Sync JPA field
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public double getWeight() {
        return weight.get();
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
        this.weightValue = weight; // Sync JPA field
    }
}