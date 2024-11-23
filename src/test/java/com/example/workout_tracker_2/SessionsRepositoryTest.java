package com.example.workout_tracker_2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SessionsRepositoryTest {

    @Autowired
    private SessionsRepository sessionsRepository;

    @Autowired
    private TypesRepository typesRepository;

    @Test
    public void testSaveAndRetrieveSessions() {
        Types type = new Types();
        type.setName("Yoga");
        typesRepository.save(type);

        Sessions session = new Sessions();
        session.setDate("2024-11-23");
        session.setType(type);
        session.setDuration(45);
        sessionsRepository.save(session);

        List<Sessions> sessions = sessionsRepository.findAll();
        assertEquals(1, sessions.size());
        assertEquals("Yoga", sessions.get(0).getType().getName());
    }
}
