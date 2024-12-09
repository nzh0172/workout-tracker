package com.example.workout_tracker_2.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false) // Make nullable if optional
    @JsonBackReference
    private Workout workout;

    @Column(name = "name", nullable = false)
    private String name; // Single field for JPA and UI

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ExerciseSet> sets = new ArrayList<>();

    @Transient
    private final StringProperty nameProperty = new SimpleStringProperty(); // For JavaFX binding

    // Default Constructor
    public Exercise() {
        this.nameProperty.addListener((obs, oldVal, newVal) -> this.name = newVal); // Sync JavaFX property
    }

    // Parameterized Constructor for JPA
    public Exercise(Long id, String name, List<ExerciseSet> sets) {
        this();
        this.id = id;
        this.name = name;
        this.nameProperty.set(name); // Sync JavaFX property
        if (sets != null) {
            this.sets = sets;
        }
    }

    // Parameterized Constructor for UI
    public Exercise(String name) {
        this();
        this.name = name;
        this.nameProperty.set(name); // Sync JavaFX property
    }

    @PostLoad
    private void syncAfterLoad() {
        this.nameProperty.set(this.name); // Synchronize JavaFX property with JPA field
    }

    // Getters and Setters for JPA
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameProperty.set(name); // Sync JavaFX property
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    // Getters and Setters for JavaFX Properties
    public StringProperty nameProperty() {
        return nameProperty;
    }

    @Override
    public String toString() {
        return "Exercise{id=" + id + ", name='" + name + "'}";
    }

}