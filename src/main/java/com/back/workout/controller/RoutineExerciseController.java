package com.back.workout.controller;

import com.back.workout.models.RoutineExercise;
import com.back.workout.services.RoutineExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routine-exercise")
public class RoutineExerciseController {
    private final RoutineExerciseService rout;

    @Autowired
    public RoutineExerciseController(RoutineExerciseService routineExerciseService){
        this.rout = routineExerciseService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createRelate(@RequestBody RoutineExercise routineExercise) {
        rout.relateRoutine(routineExercise);
        return ResponseEntity.ok().build();
    }
}