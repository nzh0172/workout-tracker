package com.example.workout_tracker_2.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@Entity
@Table(name = "workout_log")
public class WorkoutLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workout_id", nullable = false)
    @JsonBackReference
    private Workout workout;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int duration;

    // JavaFX properties
    @Transient
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    @Transient
    private final IntegerProperty durationProperty = new SimpleIntegerProperty();

    // Default Constructor
    public WorkoutLog() {
        // Sync JPA fields with JavaFX properties
        this.dateProperty.addListener((obs, oldVal, newVal) -> this.date = newVal);
        this.durationProperty.addListener((obs, oldVal, newVal) -> this.duration = newVal.intValue());
    }

    // Parameterized Constructor
    public WorkoutLog(Long id, Workout workout, LocalDate date, int duration) {
        this();
        this.id = id;
        this.workout = workout;
        setDate(date); // Sync JavaFX property
        setDuration(duration); // Sync JavaFX property
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.dateProperty.set(date); // Sync JavaFX property
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        this.durationProperty.set(duration); // Sync JavaFX property
    }

    // Getters and Setters for JavaFX Properties
    public ObjectProperty<LocalDate> dateProperty() {
        return dateProperty;
    }

    public LocalDate getDateProperty() {
        return dateProperty.get();
    }

    public void setDateProperty(LocalDate date) {
        this.dateProperty.set(date);
        this.date = date; // Sync JPA field
    }

    public IntegerProperty durationProperty() {
        return durationProperty;
    }

    public int getDurationProperty() {
        return durationProperty.get();
    }

    public void setDurationProperty(int duration) {
        this.durationProperty.set(duration);
        this.duration = duration; // Sync JPA field
    }

    @Override
    public String toString() {
        return "WorkoutLog{" +
                "id=" + id +
                ", workout=" + (workout != null ? workout.getName() : "null") +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}