package com.example.workout_tracker_2;

import com.example.workout_tracker_2.service.ExerciseService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ApplicationContextTest {

    @Autowired
    private ExerciseService exerciseService;

    @Test
    void testExerciseServiceBean() {
    	assertNotNull(exerciseService, "ExerciseService bean should be loaded");
    }
}