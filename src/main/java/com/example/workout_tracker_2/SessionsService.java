package com.example.workout_tracker_2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SessionsService {

    private final SessionsRepository sessionsRepository;
    private final TypesRepository typesRepository;

    public SessionsService(SessionsRepository sessionsRepository, TypesRepository typesRepository) {
        this.sessionsRepository = sessionsRepository;
        this.typesRepository = typesRepository;
    }

    public Sessions saveSession(Sessions session) {
        return sessionsRepository.save(session);
    }

    public List<Sessions> getAllSessions() {
        return sessionsRepository.findAll();
    }
    
    @Transactional
    public Sessions createSession(String date, Long typeId, Integer duration) {
        // Fetch the Types entity from the database
        Types type = typesRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Type not found"));

        // Create the Sessions object
        Sessions session = new Sessions();
        session.setDate(date);
        session.setType(type); // Use the persistent Types object
        session.setDuration(duration);

        // Save the session
        return sessionsRepository.save(session);
    }
}
