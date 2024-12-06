package com.example.workout_tracker_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workout_tracker_2.entity.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}