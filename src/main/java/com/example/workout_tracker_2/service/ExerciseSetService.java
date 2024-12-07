package com.example.workout_tracker_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.repository.ExerciseSetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseSetService {

    private final ExerciseSetRepository exerciseSetRepository;

    @Autowired  // Marks this constructor for Spring's dependency injection
    public ExerciseSetService(ExerciseSetRepository exerciseSetRepository) {
        this.exerciseSetRepository = exerciseSetRepository;
    }

    // Get all ExerciseSets
    public List<ExerciseSet> getAllExerciseSets() {
        return exerciseSetRepository.findAll();
    }

    // Save an ExerciseSet
    public ExerciseSet saveExerciseSet(ExerciseSet exerciseSet) {
        return exerciseSetRepository.save(exerciseSet);
    }

    // Delete an ExerciseSet by ID
    public void deleteExerciseSet(Long id) {
        exerciseSetRepository.deleteById(id);
    }

    // Find an ExerciseSet by ID
    public Optional<ExerciseSet> findById(Long id) {
        return exerciseSetRepository.findById(id);
    }

    // Save (or update) an ExerciseSet
    public ExerciseSet save(ExerciseSet set) {
        return exerciseSetRepository.save(set);
    }
}