package com.example.workout_tracker_2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionExercisesRepository extends JpaRepository<SessionExercises, Long> {

    // Find all exercises by session ID
    List<SessionExercises> findBySessionId(Long sessionId);

    // Find all sessions by exercise ID
    List<SessionExercises> findByExerciseId(Long exerciseId);

    // Optional: Custom delete by session and exercise
    void deleteBySessionIdAndExerciseId(Long sessionId, Long exerciseId);
}
