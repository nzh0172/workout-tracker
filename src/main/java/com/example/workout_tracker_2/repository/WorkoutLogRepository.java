package com.example.workout_tracker_2.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.workout_tracker_2.entity.WorkoutLog;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    Optional<WorkoutLog> findByWorkoutIdAndDate(Long workoutId, LocalDate date);

	List<WorkoutLog> findByWorkoutIdAndDate(Long workoutId, LocalDate date, Sort sort);

}