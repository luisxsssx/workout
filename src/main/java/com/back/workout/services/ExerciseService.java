package com.back.workout.services;

import com.back.workout.models.ExerciseModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {
    private final JdbcTemplate jdbcTemplate;

    public ExerciseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createExercise(ExerciseModel exerciseModel) {
        String sql = "CALL sp_create_exercise(?, ?, ?, ?)";

        if (exerciseModel == null || exerciseModel.getName() == null || exerciseModel.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The exercise name cannot be empty.");
        }

        jdbcTemplate.update(sql,
                exerciseModel.getName(),
                exerciseModel.getTrackingType().getValue(),
                exerciseModel.getDescription(),
                exerciseModel.getEquipment()
        );
    }
}