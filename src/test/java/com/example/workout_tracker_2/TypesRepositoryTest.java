package com.example.workout_tracker_2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TypesRepositoryTest {

    @Autowired
    private TypesRepository typesRepository;

    @Test
    public void testSaveAndRetrieveTypes() {
        Types type = new Types();
        type.setName("Strength");
        typesRepository.save(type);

        Optional<Types> retrievedType = typesRepository.findByName("Strength");
        assertEquals("Strength", retrievedType.get().getName());
    }
}
