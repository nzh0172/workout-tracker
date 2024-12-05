package com.example.workout_tracker_2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercises {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "session_exercises",  // name of the join table
        joinColumns = @JoinColumn(name = "exercise_id"),  // foreign key for exercises
        inverseJoinColumns = @JoinColumn(name = "session_id")  // foreign key for sessions
    )
    @JsonBackReference  // To prevent infinite recursion when serializing
    private List<Sessions> sessions;  // Many exercises can be part of one session

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "sets")
    private int sets;
    
    @Column(name = "reps")
    private int reps;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Sessions> getSessions() {
        return sessions;
    }

    public void setSessions(List<Sessions> sessions) {
        this.sessions = sessions;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
