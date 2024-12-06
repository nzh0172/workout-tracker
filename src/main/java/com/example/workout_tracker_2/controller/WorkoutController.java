package com.example.workout_tracker_2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.workout_tracker_2.entity.Workout;
import com.example.workout_tracker_2.service.WorkoutService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        return ResponseEntity.ok(workoutService.saveWorkout(workout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id, @RequestBody Workout updatedWorkout) {
        Optional<Workout> existingWorkout = workoutService.findById(id);
        if (existingWorkout.isPresent()) {
            Workout workout = existingWorkout.get();
            workout.setName(updatedWorkout.getName());
            return ResponseEntity.ok(workoutService.save(workout));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}