package com.example.workout_tracker_2;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionExercisesService {

    private final SessionExercisesRepository repository;

    public SessionExercisesService(SessionExercisesRepository repository) {
        this.repository = repository;
    }

    // Add a new session-exercise relationship
    public SessionExercises addSessionExercise(SessionExercises sessionExercise) {
        return repository.save(sessionExercise);
    }

    // Get all exercises for a session
    public List<SessionExercises> getExercisesBySession(Long sessionId) {
        return repository.findBySessionId(sessionId);
    }

    // Get all sessions for an exercise
    public List<SessionExercises> getSessionsByExercise(Long exerciseId) {
        return repository.findByExerciseId(exerciseId);
    }

    // Delete a session-exercise relationship
    public void deleteSessionExercise(Long sessionId, Long exerciseId) {
        repository.deleteBySessionIdAndExerciseId(sessionId, exerciseId);
    }
    
    public List<SessionExercises> getAllSessionExercises() {
        return repository.findAll();
    }
}
