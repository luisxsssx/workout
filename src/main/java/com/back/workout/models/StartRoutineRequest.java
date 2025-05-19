package com.back.workout.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StartRoutineRequest {
    private Integer routine_id;
    private Integer exercise_id;
    private Integer user_id;
}
