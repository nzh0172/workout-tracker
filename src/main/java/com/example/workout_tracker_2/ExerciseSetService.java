package com.example.workout_tracker_2;

import org.springframework.stereotype.Service;
import java.util.List;

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
}