package com.example.workout_tracker_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionsController {

    @Autowired
    private SessionsService sessionsService;

    @GetMapping
    public List<Sessions> getAllSessions() {
        return sessionsService.getAllSessions();
    }

    @PostMapping
    public Sessions addSession(@RequestBody Sessions session) {
        return sessionsService.saveSession(session);
    }
    
}
