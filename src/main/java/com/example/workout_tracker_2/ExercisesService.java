package com.example.workout_tracker_2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExercisesService {

    private final ExercisesRepository exercisesRepository;

    public ExercisesService(ExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }

    // Fetch all exercises
    public List<Exercises> getAllExercises() {
        return exercisesRepository.findAll();
    }

    // Fetch exercises by name
    public List<Exercises> getExercisesByName(String exerciseName) {
        return exercisesRepository.findByExerciseName(exerciseName);
    }

    // Add a new exercise
    public Exercises saveExercise(Exercises exercise) {
        return exercisesRepository.save(exercise);
    }

    // Update exercise
    public Exercises updateExercise(Long id, Exercises exercise) {
        Optional<Exercises> existingExercise = exercisesRepository.findById(id);
        if (existingExercise.isPresent()) {
            Exercises updatedExercise = existingExercise.get();
            updatedExercise.setExerciseName(exercise.getExerciseName());
            updatedExercise.setSets(exercise.getSets());
            updatedExercise.setReps(exercise.getReps());
            updatedExercise.setWeight(exercise.getWeight()); // Include the weight update

            //updatedExercise.setSessionId(exercise.getSessionId());
            //updatedExercise.setTypeId(exercise.getTypeId());
            return exercisesRepository.save(updatedExercise);
        }
        return null;  // Handle better with exception if required
    }

    // Delete exercise by ID
    public void deleteExerciseById(Long id) {
        exercisesRepository.deleteById(id);
    }

    // Delete exercise by name
    public void deleteExerciseByName(String exerciseName) {
        exercisesRepository.deleteByExerciseName(exerciseName);
    }
}
