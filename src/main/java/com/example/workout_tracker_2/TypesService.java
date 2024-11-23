package com.example.workout_tracker_2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypesService {

    private final TypesRepository typesRepository;

    public TypesService(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }

    public Types saveType(Types type) {
        return typesRepository.save(type);
    }

    public Optional<Types> findByName(String name) {
        return typesRepository.findByName(name);
    }

	public List<Types> getAllTypes() {
		return typesRepository.findAll();
	}
}
