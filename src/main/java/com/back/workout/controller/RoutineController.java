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
@RequestMapping("/routines")
public class RoutineController {

    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @PostMapping
    public ResponseEntity<Integer> createRoutine(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");
        Integer userId = (Integer) request.get("user_id");
        Integer[] exerciseIds = ((List<Integer>) request.get("exerciseIds")).toArray(new Integer[0]);

        Integer routineId = routineService.createRoutineWithExercises(Long.valueOf(userId), name, description, exerciseIds);
        return ResponseEntity.ok(routineId);
    }

    @GetMapping
    public List<RoutineModel> getAllRoutines() {
        return routineService.listRoutines();
    }

    @PostMapping("/by-id")
    public ResponseEntity<List<RoutineModel>> getRoutineById(@RequestBody RoutineModel routineRequest) {
        List<RoutineModel> routines = routineService.getRoutineById(routineRequest.getId_routine());
        if (routines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(routines);
    }

    @PostMapping("/by-user")
    public ResponseEntity<List<RoutineModel>> getRoutinesByUserId(@RequestBody RoutineModel routineModel) {
        List<RoutineModel> routines = routineService.getRoutineByUserId(routineModel.getUser_id());
        if (routines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(routines);
    }

    // RoutineController.java
    @PostMapping("/summary")
    public ResponseEntity<String> getRoutineSummaryById(@RequestBody RoutineModel routineModel) {
        String result = routineService.listRoutinesById(
                routineModel.getId_routine(),
                routineModel.getUser_id()
        );
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/by-id")
    public ResponseEntity<?> deleteRoutine(@RequestBody RoutineModel routineModel) {
        try {
            routineService.deleteRoutine(routineModel);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting routine: " + e.getMessage());
        }
    }

    @PostMapping("/status/update")
    public ResponseEntity<StatusUpdateResult> updateRoutineStatus(@RequestBody StatusUpdateRequest request) {
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
