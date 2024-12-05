package com.example.workout_tracker_2;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
@Embeddable
public class SessionExerciseKey implements Serializable {

    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "exercise_id")
    private Long exerciseId;

    // Default constructor
    public SessionExerciseKey() {
    }

    // Parameterized constructor
    public SessionExerciseKey(Long sessionId, Long exerciseId) {
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
    }

    // Getters and Setters
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionExerciseKey that = (SessionExerciseKey) o;
        return Objects.equals(sessionId, that.sessionId) &&
               Objects.equals(exerciseId, that.exerciseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, exerciseId);
    }
}
