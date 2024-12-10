package com.example.workout_tracker_2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.Workout;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // Find all exercises by workout ID
    List<Exercise> findByWorkoutId(Long workoutId);
    Optional<Exercise> findByNameAndWorkout(String name, Workout workout);
    @Modifying
    @Transactional
    @Query("DELETE FROM Exercise e WHERE e.id = :id")
    void deleteByIdCustom(@Param("id") Long id);
}