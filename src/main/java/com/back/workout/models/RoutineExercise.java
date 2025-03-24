package com.back.workout.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExercise {
    public Integer routine_id;
    public Integer exercise;
    public Integer sets;
    public Integer reps;
    public Integer duration;
    public Integer rest_interval;
}