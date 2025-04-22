package com.back.workout.controller;

import com.back.workout.models.ExerciseModel;
import com.back.workout.models.ExerciseModelName;
import com.back.workout.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/kill")
    public ResponseEntity<Void> deleteExercise(@RequestBody ExerciseModel exerciseModel) {
        try {
            ExerciseModel exerciseModel1 = new ExerciseModel();
            exerciseModel1.setId_exercise(exerciseModel.getId_exercise());
            exerciseService.deleteExercise(exerciseModel);
            return  ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    public List<ExerciseModel> getExercises() {
        return exerciseService.listExercises();
    }

    @GetMapping("/names")
    public List<ExerciseModelName> getExercisesName() {
        return exerciseService.listExercisesName();
    }

    @PostMapping("/id")
    public ResponseEntity<Object> getExercisesById(@RequestBody ExerciseModel exerciseModel) throws Exception {
        Object result = exerciseService.getExercisesById(exerciseModel.getId_exercise());
        return ResponseEntity.ok(result);
    }
}