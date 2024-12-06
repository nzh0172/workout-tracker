package com.example.workout_tracker_2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
}