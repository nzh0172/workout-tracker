package com.example.workout_tracker_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.workout_tracker_2.entity.Exercise;
import com.example.workout_tracker_2.entity.ExerciseSet;
import com.example.workout_tracker_2.entity.WorkoutLog;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    List<ExerciseSet> findByExerciseId(Long exerciseId);

	List<ExerciseSet> findByExerciseAndWorkoutLog(Exercise exercise, WorkoutLog workoutLog);
}