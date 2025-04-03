package com.back.workout.services;

import com.back.workout.models.RoutineExercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RoutineExerciseService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoutineExerciseService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // This get is for routines that have exercises, that is, that have relationships
    public void relateRoutine(RoutineExercise rout) {
        String sql = "CALL sp_routine_exercise(?, ?)";
        jdbcTemplate.update(sql,
                rout.getRoutine_id(),
                rout.getExercise_id()
        );
    }
}