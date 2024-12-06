package com.example.workout_tracker_2;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;

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
}