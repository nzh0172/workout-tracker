package com.example.workout_tracker_2.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.workout_tracker_2.entity.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    @EntityGraph(attributePaths = "exercises")
    Optional<Workout> findWithExercisesById(Long id);
}