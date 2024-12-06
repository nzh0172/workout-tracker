package com.example.workout_tracker_2.service;

import org.springframework.stereotype.Service;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.repository.ExerciseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
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

    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
}