package com.example.workout_tracker_2;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "session_exercises")
public class SessionExercises {

    @EmbeddedId
    private SessionExerciseKey id;

    @ManyToOne
    @MapsId("sessionId") // Maps to the sessionId in the composite key
    @JoinColumn(name = "session_id")
    private Sessions session;

    @ManyToOne
    @MapsId("exerciseId") // Maps to the exerciseId in the composite key
    @JoinColumn(name = "exercise_id")
    private Exercises exercise;

    // Default constructor
    public SessionExercises() {
    }

    // Parameterized constructor
    public SessionExercises(Sessions session, Exercises exercise) {
        this.session = session;
        this.exercise = exercise;
        this.id = new SessionExerciseKey(session.getId(), exercise.getId());
    }

    // Getters and Setters
    public SessionExerciseKey getId() {
        return id;
    }

    public void setId(SessionExerciseKey id) {
        this.id = id;
    }

    public Sessions getSession() {
        return session;
    }

    public void setSession(Sessions session) {
        this.session = session;
    }

    public Exercises getExercise() {
        return exercise;
    }

    public void setExercise(Exercises exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionExercises that = (SessionExercises) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
