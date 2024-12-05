package com.example.workout_tracker_2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
public class Sessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;
    
    @Column(name = "date")
    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    @JsonBackReference  // To prevent infinite recursion when serializing
    private Types type;

    @Column(name = "duration")
    private Integer duration;
    
    @JsonProperty("type_name")  // Custom name for the type property
    public String getTypeName() {
        return type.getName();  // Return only the name of the type
    }
    
    @ManyToMany(mappedBy = "sessions")
    @JsonManagedReference  // To allow serialization of exercises without infinite recursion
    private List<Exercises> exercises; // One session can have many exercises

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Exercises> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercises> exercises) {
        this.exercises = exercises;
    }
}
