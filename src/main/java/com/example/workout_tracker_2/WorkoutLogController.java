package com.example.workout_tracker_2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @PostMapping
    public ResponseEntity<WorkoutLog> createLog(@RequestBody WorkoutLog workoutLog) {
        return ResponseEntity.ok(workoutLogService.saveLog(workoutLog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        workoutLogService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}