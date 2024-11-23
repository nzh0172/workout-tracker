package com.example.workout_tracker_2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @GetMapping
    public List<Types> getAllTypes() {
        return typesService.getAllTypes();
    }

    @PostMapping
    public Types addType(@RequestBody Types type) {
        return typesService.saveType(type);
    }
}
