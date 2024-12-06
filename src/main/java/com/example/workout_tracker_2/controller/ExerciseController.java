package com.example.workout_tracker_2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.service.ExerciseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        return ResponseEntity.ok(exerciseService.saveExercise(exercise));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise updatedExercise) {
        Optional<Exercise> existingExercise = exerciseService.findById(id);
        if (existingExercise.isPresent()) {
            Exercise exercise = existingExercise.get();
            exercise.setName(updatedExercise.getName());
            exercise.setWorkout(updatedExercise.getWorkout());
            return ResponseEntity.ok(exerciseService.save(exercise));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}