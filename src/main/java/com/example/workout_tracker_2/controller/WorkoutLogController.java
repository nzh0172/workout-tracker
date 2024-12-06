package com.example.workout_tracker_2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.workout_tracker_2.entity.WorkoutLog;
import com.example.workout_tracker_2.service.WorkoutLogService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workout-logs")
public class WorkoutLogController {

    private final WorkoutLogService workoutLogService;

    public WorkoutLogController(WorkoutLogService workoutLogService) {
        this.workoutLogService = workoutLogService;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutLog>> getAllLogs() {
        return ResponseEntity.ok(workoutLogService.getAllLogs());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutLog> getWorkoutLog(@PathVariable Long id) {
        Optional<WorkoutLog> workoutLog = workoutLogService.findById(id);
        if (workoutLog.isPresent()) {
            return ResponseEntity.ok(workoutLog.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<WorkoutLog> createLog(@RequestBody WorkoutLog workoutLog) {
        return ResponseEntity.ok(workoutLogService.saveLog(workoutLog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        workoutLogService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutLog> updateWorkoutLog(@PathVariable Long id, @RequestBody WorkoutLog updatedLog) {
        Optional<WorkoutLog> existingLog = workoutLogService.findById(id);
        if (existingLog.isPresent()) {
            WorkoutLog log = existingLog.get();
            log.setDate(updatedLog.getDate());
            log.setDuration(updatedLog.getDuration());
            log.setWorkout(updatedLog.getWorkout());
            return ResponseEntity.ok(workoutLogService.save(log));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}





