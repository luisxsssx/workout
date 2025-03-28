package com.back.workout.services;

import com.back.workout.models.RoutineModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoutineService {

    private final JdbcTemplate jdbcTemplate;

    public RoutineService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create routine
    public void createRoutine(RoutineModel routineModel) {
        String sql = "CALL sp_create_routine(?, ?, ?, ?)";

        if (routineModel == null || routineModel.getName() == null || routineModel.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The routine name cannot be empty.");
        }

        routineModel.setCreated_at(LocalDate.now());
        routineModel.setUpdated_at(LocalDate.now());
        jdbcTemplate.update(sql, routineModel.getName(), routineModel.getDescription(), routineModel.getCreated_at(), routineModel.getUpdated_at());
    }

    // Get routines
    public List<RoutineModel> listRoutines() {
        String sql = "SELECT * FROM get_routines()";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new RoutineModel(
                        rs.getInt("p_id_routine"),
                        rs.getString("p_name"),
                        rs.getString("p_description"),
                        rs.getDate("p_created_at").toLocalDate(),
                        rs.getDate("p_updated_at").toLocalDate()
                )
        );
    }

}