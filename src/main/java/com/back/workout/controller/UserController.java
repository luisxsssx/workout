package com.back.workout.controller;

import com.back.workout.models.LoginRequest;
import com.back.workout.models.UserModel;
import com.back.workout.models.UserRequestDto;
import com.back.workout.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserModel userModel) {
        try {
            userService.registerUser(userModel);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/get")
    public ResponseEntity<?> getUsers(@RequestBody UserRequestDto userRequestDto) {
        try {
            Object result = userService.getUsers(userRequestDto.getType(), userRequestDto.getValue());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid type: " + userRequestDto.getType());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        UserModel user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if(user == null) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        user.setPassword(null);
        return ResponseEntity.ok("Login successfully");
    }
}