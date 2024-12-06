package com.example.workout_tracker_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workout_tracker_2.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}