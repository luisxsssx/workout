package com.back.workout.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseModel {
    public Integer id_exercise;
    public String name;
    public TrackingType tracking_type;
    public String description;
    public String equipment;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;

    public ExerciseModel(int id_exercise, String name, String tracking_type, String description, String equipment, LocalDate created_at, LocalDate updated_at) {
        this.id_exercise = id_exercise;
        this.name = name;
        this.tracking_type = TrackingType.valueOf(tracking_type);
        this.description = description;
        this.equipment = equipment;
        this.created_at = LocalDateTime.from(created_at);
        this.updated_at = updated_at.atStartOfDay();
    }
}