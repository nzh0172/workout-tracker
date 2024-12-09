package com.example.workout_tracker_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.Workout;
import com.example.workout_tracker_2.repository.ExerciseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    } 

	public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }
	
    // Method to get exercises by workout ID
    public List<Exercise> getExercisesByWorkoutId(Long workoutId) {
        return exerciseRepository.findByWorkoutId(workoutId);
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
    
    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }
    
    public Optional<Exercise> findByNameAndWorkout(String name, Workout workout) {
        return exerciseRepository.findByNameAndWorkout(name, workout);
    }

    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
}