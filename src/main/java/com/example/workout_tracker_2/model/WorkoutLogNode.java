package com.example.workout_tracker_2.model;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class WorkoutLogNode {
    private final StringProperty name;    // For workout name or exercise name
    private final StringProperty date;    // For workout log date
    private final StringProperty value;   // For duration or metadata
    private final IntegerProperty reps;   // For exercises
    private final IntegerProperty sets;   // For exercises
    private final ObjectProperty<BigDecimal> weight; // Weight for exercises

    // Constructor
    public WorkoutLogNode(String name, String date, String value, Integer reps, Integer sets, BigDecimal weight) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.value = new SimpleStringProperty(value);
        this.reps = reps == null ? null : new SimpleIntegerProperty(reps);
        this.sets = sets == null ? null : new SimpleIntegerProperty(sets);
        this.weight = weight == null ? null : new SimpleObjectProperty<>(weight);
    }

    // Name Property
    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    // Date Property
    public StringProperty dateProperty() {
        return date;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    // Value Property
    public StringProperty valueProperty() {
        return value;
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    // Reps Property
    public IntegerProperty repsProperty() {
        return reps;
    }

    public Integer getReps() {
        return reps == null ? null : reps.get();
    }

    public void setReps(Integer reps) {
        if (this.reps != null) {
            this.reps.set(reps);
        }
    }

    // Sets Property
    public IntegerProperty setsProperty() {
        return sets;
    }

    public Integer getSets() {
        return sets == null ? null : sets.get();
    }

    public void setSets(Integer sets) {
        if (this.sets != null) {
            this.sets.set(sets);
        }
    }

    // Weight Property
    public ObjectProperty<BigDecimal> weightProperty() {
        return weight;
    }

    public BigDecimal getWeight() {
        return weight == null ? null : weight.get();
    }

    public void setWeight(BigDecimal weight) {
        if (this.weight != null) {
            this.weight.set(weight);
        }
    }
}