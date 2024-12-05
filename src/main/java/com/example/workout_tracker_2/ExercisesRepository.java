package com.example.workout_tracker_2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExercisesRepository extends JpaRepository<Exercises, Long> {
    List<Exercises> findByExerciseName(String exerciseName);
    void deleteByExerciseName(String exerciseName);
}
