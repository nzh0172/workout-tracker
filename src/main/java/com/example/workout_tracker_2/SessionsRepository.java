package com.example.workout_tracker_2;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository<Sessions, Long> {
}
