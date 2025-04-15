package com.back.workout.controller;

import com.back.workout.models.RoutineModel;
import com.back.workout.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/routine")
public class RoutineController {
    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> createRoutine(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");
        Integer[] exerciseIds = ((java.util.List<Integer>) request.get("exerciseIds")).toArray(new Integer[0]);

        Integer routineId = routineService.createRoutineWithExercises(name, description, exerciseIds);
        return ResponseEntity.ok(routineId);
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
    public ResponseEntity<?> deleteExercise(@RequestBody RoutineModel routineModel) {
        try {
            RoutineModel routineModel1 = new RoutineModel();
            routineModel1.setId_routine(routineModel.getId_routine());
            routineService.deleteRoutine(routineModel);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}