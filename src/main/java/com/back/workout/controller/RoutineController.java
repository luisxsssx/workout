package com.back.workout.controller;

import com.back.workout.models.RoutineModel;
import com.back.workout.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

     @PostMapping("/get")
     public ResponseEntity<List<RoutineModel>> getRoutine(@RequestBody RoutineModel routineRequest) {
         List<RoutineModel> routines = routineService.getRoutineById(routineRequest.getId_routine());
         if (routines.isEmpty()) {
             return ResponseEntity.noContent().build();
         }
         return ResponseEntity.ok(routines);
     }

    @PostMapping("/detail/id")
    public ResponseEntity<Object> getRoutinesId (@RequestBody RoutineModel routineModel) {
        String result = routineService.listRoutinesById(routineModel.getId_routine());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/kill")
    public String deleteExercise(@RequestBody RoutineModel routineModel) {
        routineService.deleteRoutine(routineModel);
        return "Routine removed Successfully";
    }
}


