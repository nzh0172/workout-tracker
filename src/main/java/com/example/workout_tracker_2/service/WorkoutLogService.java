package com.example.workout_tracker_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.workout_tracker_2.entity.WorkoutLog;
import com.example.workout_tracker_2.repository.WorkoutLogRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;


@Service
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;

    @Autowired
    public WorkoutLogService(WorkoutLogRepository workoutLogRepository) {
        this.workoutLogRepository = workoutLogRepository;
    }

    public List<WorkoutLog> getAllLogs() {
        return workoutLogRepository.findAll();
    }
    
    @Transactional
    public List<WorkoutLog> getAllLogsWithDetails() {
        List<WorkoutLog> logs = workoutLogRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        logs.forEach(log -> {
            log.getWorkout().getExercises().forEach(exercise -> {
                exercise.getSets().size(); // Force initialization of the sets collection
            });
        });
        return logs;
    }

    public WorkoutLog saveLog(WorkoutLog workoutLog) {
        return workoutLogRepository.save(workoutLog);
    }

    public void deleteLog(Long id) {
        workoutLogRepository.deleteById(id);
    }
    
    public Optional<WorkoutLog> findById(Long id) {
        return workoutLogRepository.findById(id);
    }

    public WorkoutLog save(WorkoutLog log) {
        return workoutLogRepository.save(log);
    }
    
    @Transactional //Ensure the session is active
    public Optional<WorkoutLog> findByWorkoutAndDate(Long workoutId, LocalDate date) {
        return workoutLogRepository.findByWorkoutIdAndDate(workoutId, date);
    }
   
	 // Fetch the latest WorkoutLog for today
	 public Optional<WorkoutLog> findLatestByWorkoutAndDate(Long workoutId, LocalDate date) {
	     List<WorkoutLog> logs = workoutLogRepository.findByWorkoutIdAndDate(workoutId, date, Sort.by(Sort.Direction.DESC, "id"));
	     return logs.isEmpty() ? Optional.empty() : Optional.of(logs.get(0)); // Return the most recent log
	 }

	public void deleteAllLogs() {
		workoutLogRepository.deleteAll();
	}


    
    
}