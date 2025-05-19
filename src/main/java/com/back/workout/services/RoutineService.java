package com.back.workout.services;

import com.back.workout.models.RoutineModel;
import com.back.workout.models.StatusUpdateResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Integer createRoutineWithExercises(Long user_id, String name, String description, Integer[] exerciseIds) {
        try {
            String sql = "SELECT create_routine_with_exercises(?, ?, ?, ?)";
            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{user_id, name, description, exerciseIds},
                    Integer.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Error creating routine: " + e.getMessage());
        }
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
                        rs.getDate("p_updated_at").toLocalDate(),
                        rs.getInt("p_user_id"),
                        rs.getString("p_status")
                )
        );
    }

    // Get a user's routines by user_id
    public List<RoutineModel> getRoutineByUserId(int user_id) {
        String sql = "SELECT * FROM get_routines_by_user(?)";
        return jdbcTemplate.query(
                sql,
                new Object[]{user_id},
                (rs, rowNum) -> mapRowToRoutine(rs)
        );
    }

    public List<RoutineModel> getRoutineById(int id_routine) {
        String sql = "SELECT * FROM get_routine(?)";

        return jdbcTemplate.query(
                sql,
                new Object[]{id_routine},
                (rs, rowNum) -> mapRowToRoutine(rs)
        );
    }

    private RoutineModel mapRowToRoutine(ResultSet rs) throws SQLException {
        return new RoutineModel(
                rs.getInt("p_id_routine"),
                rs.getString("p_name"),
                rs.getString("p_description"),
                rs.getDate("p_created_at").toLocalDate(),
                rs.getDate("p_updated_at").toLocalDate(),
                rs.getInt("p_user_id"),
                rs.getString("p_status")
        );
    }

    // Routines by id with relation
    public String listRoutinesById(Integer id_routine) {
        String sql = "SELECT get_routine_exercises(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{id_routine}, String.class);
    }

    public void deleteRoutine(RoutineModel routineModel) {
        String sql = "CALL delete_routine(?)";
        jdbcTemplate.update(sql, routineModel.getId_routine());
    }

    // Execute routine
    public Integer executeRoutine(Integer routine_id, Integer exercise_id, Integer user_id) {
        String query = "CALL sp_start_routine(?, ?, ?)";
        return jdbcTemplate.update(query, routine_id, exercise_id, user_id);
    }

    // Change routine status
    public StatusUpdateResult changeStatus(String table, Integer[] ids, String status) {
        String query = "SELECT * FROM update_status(?, ?, ?)";
        return jdbcTemplate.queryForObject(query, new Object[]{table, ids, status}, (rs, rowNum) -> {
            StatusUpdateResult result = new StatusUpdateResult();
            result.setMessage(rs.getString("message"));
            result.setRowsAffected(rs.getInt("rows_affected"));
            return result;
        });    }
}