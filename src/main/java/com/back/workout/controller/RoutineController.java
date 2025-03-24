package com.back.workout.controller;

import com.back.workout.models.RoutineModel;
import com.back.workout.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}


