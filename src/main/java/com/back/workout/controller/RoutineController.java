package com.back.workout.controller;

import com.back.workout.models.RoutineModel;
import com.back.workout.models.StartRoutineRequest;
import com.back.workout.models.StatusUpdateRequest;
import com.back.workout.models.StatusUpdateResult;
import com.back.workout.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Integer user_id = (Integer) request.get("user_id");
        Integer[] exerciseIds = ((java.util.List<Integer>) request.get("exerciseIds")).toArray(new Integer[0]);

        Integer routineId = routineService.createRoutineWithExercises(Long.valueOf(user_id), name, description, exerciseIds);
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

     @PostMapping("/getu")
     public ResponseEntity<List<RoutineModel>> getRoutinesByUserId(@RequestBody RoutineModel routineModel) {
        List<RoutineModel> routine = routineService.getRoutineByUserId(routineModel.getUser_id());
        if(routine.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(routine);
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

    // Change routine status
    @PostMapping("/change")
    public ResponseEntity<StatusUpdateResult> changeStatus(@RequestBody StatusUpdateRequest request) {
        try {
            StatusUpdateResult result = routineService.changeStatus(
                    request.getTable(),
                    request.getIds(),
                    request.getStatus()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Start routine
    @PostMapping("/start")
    public ResponseEntity<String> startRoutine(@RequestBody StartRoutineRequest request) {
        try {
            routineService.executeRoutine(
                    request.getRoutine_id(),
                    request.getExercise_id(),
                    request.getUser_id()
            );
            return ResponseEntity.ok("Routine started successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error starting routine: " + e.getMessage());
        }
    }

}