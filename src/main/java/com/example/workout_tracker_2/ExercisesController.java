package com.example.workout_tracker_2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExercisesController {

    private final ExercisesService exercisesService;

    public ExercisesController(ExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    // Get all exercises
    @GetMapping
    public ResponseEntity<List<Exercises>> getAllExercises() {
        return ResponseEntity.ok(exercisesService.getAllExercises());
    }

    // Get exercises by name
    @GetMapping("/name/{exerciseName}")
    public ResponseEntity<List<Exercises>> getExercisesByName(@PathVariable String exerciseName) {
        return ResponseEntity.ok(exercisesService.getExercisesByName(exerciseName));
    }

    // Add a new exercise
    @PostMapping
    public ResponseEntity<Exercises> addExercise(@RequestBody Exercises exercise) {
        return ResponseEntity.ok(exercisesService.saveExercise(exercise));
    }

    // Update an exercise
    @PutMapping("/{id}")
    public ResponseEntity<Exercises> updateExercise(@PathVariable Long id, @RequestBody Exercises exercise) {
        return ResponseEntity.ok(exercisesService.updateExercise(id, exercise));
    }

    // Delete an exercise by name
    @DeleteMapping("/name/{exerciseName}")
    public ResponseEntity<String> deleteExerciseByName(@PathVariable String exerciseName) {
        exercisesService.deleteExerciseByName(exerciseName);
        return ResponseEntity.ok("Exercise deleted successfully.");
    }
}
