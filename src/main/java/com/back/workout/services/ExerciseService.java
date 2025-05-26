package com.back.workout.services;

import com.back.workout.models.ExerciseModel;
import com.back.workout.models.ExerciseModelName;
import com.back.workout.models.TrackingType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public ExerciseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get exercises by user id
    public String getExercisesByUserId(Integer user_id) throws Exception {
        String query = "SELECT get_exercise_by_user_id(?)";

        try {
            return jdbcTemplate.queryForObject(query, new Object[]{user_id}, String.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No exercise found with id: " + user_id);
        } catch (Exception e) {
            throw new Exception("Error retrieving exercise with id: " + user_id, e);
        }
    }

    // Get exercises with id
    public String getExercisesById(Integer id_exercise) throws Exception {
        String query = "SELECT get_exercise_by_id(?)";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id_exercise}, String.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No exercise found with id: " + id_exercise);
        } catch (Exception e) {
            throw new Exception("Error retrieving exercise with id: " + id_exercise, e);
        }
    }

    // Create exercise
    public void createExercise(ExerciseModel exerciseModel) {
        String sql = "CALL sp_create_exercise(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        if (exerciseModel == null || exerciseModel.getName() == null || exerciseModel.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The exercise name cannot be empty.");
        }

        jdbcTemplate.update(sql,
                exerciseModel.getName(),
                exerciseModel.getTracking_type().getValue(),
                exerciseModel.getDescription(),
                exerciseModel.getEquipment(),
                exerciseModel.getSets(),
                exerciseModel.getReps(),
                exerciseModel.getDuration(),
                exerciseModel.getRest_interval(),
                exerciseModel.getUser_id()
        );
    }

    // Get exercises name
    public List<ExerciseModelName> listExercisesName() {
        String sql = "SELECT * FROM get_exercises_name()";

        return jdbcTemplate.query(sql , (rs, rowNUm) -> {
            return new ExerciseModelName(
                    rs.getInt("p_id_exercise"),
                    rs.getString("p_name")
            );
        });
    }

    // Get exercises
    public List<ExerciseModel> listExercises() {
        String sql = "SELECT * FROM get_exercises()";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String trackingTypeStr = rs.getString("p_tracking_type");
            TrackingType trackingType = trackingTypeStr != null ? TrackingType.fromString(trackingTypeStr) : null;

            return new ExerciseModel(
                    rs.getInt("p_id_exercise"),
                    rs.getString("p_name"),
                    trackingType,
                    rs.getString("p_description"),
                    rs.getString("p_equipment"),
                    rs.getInt("p_sets"),
                    rs.getInt("p_reps"),
                    rs.getInt("p_duration"),
                    rs.getInt("p_rest_interval"),
                    rs.getInt("p_user_id"),
                    rs.getTimestamp("p_created_at").toLocalDateTime(),
                    rs.getTimestamp("p_updated_at").toLocalDateTime()
            );
        });
    }

    // Delete
    public void deleteExercise(ExerciseModel exerciseModel) {
        String sql = "CALL sp_delete_exercise(?)";
        jdbcTemplate.update(sql, exerciseModel.getId_exercise());
    }

    // Adding exercises to a routine
    public int addExercisesToRoutine(Integer routineId, List<Integer> exerciseIds) {
        String sql = "CALL sp_add_exercises_in_routine(?, ?)";
        Integer[] exerciseArray = exerciseIds.toArray(new Integer[0]);
        return jdbcTemplate.update(sql, new Object[] { exerciseArray, routineId });
    }

    // Eliminate exercises from a routine
    public int deleteExerciseRoutine(Integer exercise_id, Integer routine_id) {
        String sql = "CALL sp_delete_exercise_routine(?, ?)";
        return jdbcTemplate.update(sql, exercise_id, routine_id);
    }
}