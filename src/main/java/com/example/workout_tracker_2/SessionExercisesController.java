package com.example.workout_tracker_2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session_exercises")
public class SessionExercisesController {

    private final SessionExercisesService service;

    public SessionExercisesController(SessionExercisesService service) {
        this.service = service;
    }

    // Add a new session-exercise relationship
    @PostMapping
    public ResponseEntity<SessionExercises> addSessionExercise(@RequestBody SessionExercises sessionExercise) {
        return ResponseEntity.ok(service.addSessionExercise(sessionExercise));
    }

    // Get all exercises for a session
    @GetMapping("/sessions/{session_id}")
    public ResponseEntity<List<SessionExercises>> getExercisesBySession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(service.getExercisesBySession(sessionId));
    }

    // Get all sessions for an exercise
    @GetMapping("/exercises/{exercise_id}")
    public ResponseEntity<List<SessionExercises>> getSessionsByExercise(@PathVariable Long exerciseId) {
        return ResponseEntity.ok(service.getSessionsByExercise(exerciseId));
    }
    
    // Define a GET method to handle the GET request
    @GetMapping
    public List<SessionExercises> getAllSessionExercises() {
        return service.getAllSessionExercises();
    }

    // Delete a session-exercise relationship
    @DeleteMapping
    public ResponseEntity<Void> deleteSessionExercise(
            @RequestParam Long sessionId,
            @RequestParam Long exerciseId) {
        service.deleteSessionExercise(sessionId, exerciseId);
        return ResponseEntity.noContent().build();
    }
}
