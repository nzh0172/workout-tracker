package com.example.workout_tracker_2;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TypesRepository extends JpaRepository<Types, Long> {
    Optional<Types> findByName(String name);
}
