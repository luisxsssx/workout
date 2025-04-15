package com.back.workout.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseProgress {
    public Integer id_exercise;
    public String name;
    public String status;
}