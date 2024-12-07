package com.example.workout_tracker_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.workout_tracker_2.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}