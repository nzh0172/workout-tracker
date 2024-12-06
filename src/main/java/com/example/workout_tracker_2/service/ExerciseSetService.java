package com.example.workout_tracker_2.service;

import org.springframework.stereotype.Service;

import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.repository.ExerciseSetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseSetService {

    private final ExerciseSetRepository exerciseSetRepository;

    public ExerciseSetService(ExerciseSetRepository exerciseSetRepository) {
        this.exerciseSetRepository = exerciseSetRepository;
    }

    public List<ExerciseSet> getAllExerciseSets() {
        return exerciseSetRepository.findAll();
    }

    public ExerciseSet saveExerciseSet(ExerciseSet exerciseSet) {
        return exerciseSetRepository.save(exerciseSet);
    }

    public void deleteExerciseSet(Long id) {
        exerciseSetRepository.deleteById(id);
    }
    
    public Optional<ExerciseSet> findById(Long id) {
        return exerciseSetRepository.findById(id);
    }

    public ExerciseSet save(ExerciseSet set) {
        return exerciseSetRepository.save(set);
    }
}