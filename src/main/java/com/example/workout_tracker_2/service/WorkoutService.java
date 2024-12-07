package com.example.workout_tracker_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.workout_tracker_2.entity.Workout;
import com.example.workout_tracker_2.repository.WorkoutRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }
    

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
    
    public Optional<Workout> findById(Long id) {
        return workoutRepository.findById(id);
    }

    public Workout save(Workout workout) {
        return workoutRepository.save(workout);
    }
}