package com.example.workout_tracker_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.workout_tracker_2.entity.WorkoutLog;
import com.example.workout_tracker_2.repository.WorkoutLogRepository;

import java.util.List;
import java.util.Optional;

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
    
    
}