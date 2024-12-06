package com.example.workout_tracker_2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.service.ExerciseSetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercise-sets")
public class ExerciseSetController {

    private final ExerciseSetService exerciseSetService;

    public ExerciseSetController(ExerciseSetService exerciseSetService) {
        this.exerciseSetService = exerciseSetService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseSet>> getAllExerciseSets() {
        return ResponseEntity.ok(exerciseSetService.getAllExerciseSets());
    }

    @PostMapping
    public ResponseEntity<ExerciseSet> createExerciseSet(@RequestBody ExerciseSet exerciseSet) {
        return ResponseEntity.ok(exerciseSetService.saveExerciseSet(exerciseSet));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseSet(@PathVariable Long id) {
        exerciseSetService.deleteExerciseSet(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseSet> updateExerciseSet(@PathVariable Long id, @RequestBody ExerciseSet updatedSet) {
        Optional<ExerciseSet> existingSet = exerciseSetService.findById(id);
        if (existingSet.isPresent()) {
            ExerciseSet set = existingSet.get();
            set.setReps(updatedSet.getReps());
            set.setWeight(updatedSet.getWeight());
            set.setExercise(updatedSet.getExercise());
            return ResponseEntity.ok(exerciseSetService.save(set));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}