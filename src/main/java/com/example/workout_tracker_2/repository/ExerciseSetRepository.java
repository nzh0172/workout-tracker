package com.example.workout_tracker_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workout_tracker_2.entity.ExerciseSet;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
}