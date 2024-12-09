package com.example.workout_tracker_2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.Workout;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // Find all exercises by workout ID
    List<Exercise> findByWorkoutId(Long workoutId);
    Optional<Exercise> findByNameAndWorkout(String name, Workout workout);
}