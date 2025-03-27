package com.back.workout.controller;

import com.back.workout.models.RoutineModel;
import com.back.workout.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine")
public class RoutineController {
    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @PostMapping("/create")
    public String createRoutine(@RequestBody RoutineModel routineModel) {
        routineService.createRoutine(routineModel);
        return "Routine Created Successfully";
    }

    @GetMapping("/all")
    public List<RoutineModel> getRoutines() {
        return routineService.listRoutines();
    }
}


