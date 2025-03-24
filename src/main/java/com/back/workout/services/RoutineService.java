package com.back.workout.services;

import com.back.workout.models.RoutineModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RoutineService {

    private final JdbcTemplate jdbcTemplate;

    public RoutineService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createRoutine(RoutineModel routineModel) {
        String sql = "CALL sp_create_routine(?, ?, ?)";

        if (routineModel == null || routineModel.getName() == null || routineModel.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The routine name cannot be empty.");
        }

        routineModel.setCreated_at(LocalDate.now());
        routineModel.setUpdated_at(LocalDate.now());
        jdbcTemplate.update(sql, routineModel.getName(), routineModel.getCreated_at(), routineModel.getUpdated_at());
    }

}