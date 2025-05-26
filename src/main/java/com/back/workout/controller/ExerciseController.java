package com.back.workout.controller;

import com.back.workout.models.AddExerciseRequest;
import com.back.workout.models.ExerciseModel;
import com.back.workout.models.ExerciseModelName;
import com.back.workout.models.RoutineExercise;
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

    @PostMapping("/create")
    public String createExercise(@RequestBody ExerciseModel exerciseModel) {
        exerciseService.createExercise(exerciseModel);
        return "Exercise created successfully";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteExercise(@RequestBody ExerciseModel exerciseModel) {
        try {
            ExerciseModel exercise = new ExerciseModel();
            exercise.setId_exercise(exerciseModel.getId_exercise());
            exerciseService.deleteExercise(exercise);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/routine")
    public int deleteExercisesRoutine(@RequestBody RoutineExercise routineExercise) {
        return exerciseService.deleteExerciseRoutine(routineExercise.getExercise_id(), routineExercise.getRoutine_id());
    }

    @PostMapping("/add-exercise")
    public ResponseEntity<String> addExerciseOnRoutine(@RequestBody AddExerciseRequest request) {
        exerciseService.addExercisesToRoutine(request.getRoutine_id(), request.getExerciseIds());
        return ResponseEntity.ok("Exercises added successfully");
    }

    @GetMapping("/all")
    public List<ExerciseModel> getAllExercises() {
        return exerciseService.listExercises();
    }

    @GetMapping("/names")
    public List<ExerciseModelName> getExerciseNames() {
        return exerciseService.listExercisesName();
    }

    @PostMapping("/get-by-id")
    public ResponseEntity<Object> getExerciseById(@RequestBody ExerciseModel exerciseModel) throws Exception {
        Object result = exerciseService.getExercisesById(exerciseModel.getId_exercise());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get-by-user")
    public ResponseEntity<Object> getExercisesByUserId(@RequestBody ExerciseModel exerciseModel) throws Exception {
        Object result = exerciseService.getExercisesByUserId(exerciseModel.getUser_id());
        return ResponseEntity.ok(result);
    }
}
