package com.back.workout.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseModel {
    public Integer id_exercise;
    public String name;
    public TrackingType trackingType;
    public String description;
    public String equipment;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}