package com.back.workout.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineModel {
    @Id
    public Integer id_routine;
    public String name;
    public LocalDate created_at;
    public LocalDate updated_at;
}