package com.back.workout.controller;

import com.back.workout.models.ExerciseModel;
import com.back.workout.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/add")
    public String createExercise(@RequestBody ExerciseModel exerciseModel) {
        exerciseService.createExercise(exerciseModel);
        return "Exercise Created Successfully";
    }

    @GetMapping("/all")
    public List<ExerciseModel> getExercises() {
        return exerciseService.listExercises();
    }
}