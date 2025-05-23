package com.back.workout.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineModel {
    public Integer id_routine;
    public String name;
    public String description;
    public LocalDate created_at;
    public LocalDate updated_at;
    public Integer user_id;
    public String status;
}