package com.back.workout.services;

import com.back.workout.jwt.JwtUtil;
import com.back.workout.models.LoginRequest;
import com.back.workout.models.LoginResponse;
import com.back.workout.models.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JdbcTemplate jdbcTemplate;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public AuthService(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    public LoginResponse login(LoginRequest request) {
        String sql = "SELECT f_get_users(?, ?)";

        try {
            String jsonResult = jdbcTemplate.queryForObject(sql, String.class, "username", request.getUsername());

            if (jsonResult == null) {
                throw new RuntimeException("Invalid credentials: user not found");
            }

            Map<String, Object> result;
            try {
                result = objectMapper.readValue(jsonResult, Map.class);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing JSON result: " + e.getMessage());
            }

            System.out.println("Result de f_get_users: " + result);

            String storedUsername = (String) result.get("username");
            String storedPassword = (String) result.get("password");
            Integer user_id = (Integer) result.get("id");

            if (storedUsername == null || storedPassword == null) {
                throw new RuntimeException("Invalid credentials: incomplete data");
            }

            if (!passwordEncoder.matches(request.getPassword(), storedPassword)) {
                throw new RuntimeException("Invalid credentials: incorrect password");
            }

            Map<String, Object> customClaims = new HashMap<>();
            String token = jwtUtil.generateToken(storedUsername, user_id,customClaims);

            System.out.println("Generated token: " + token);

            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(storedUsername);

            Map<String, Object> userData = new HashMap<>();
            result.forEach((key, value) -> {
                if (!key.equals("username") && !key.equals("password")) {
                    userData.put(key, value);
                }
            });
            response.setUserData(userData);

            return response;
        } catch (org.springframework.jdbc.UncategorizedSQLException e) {
            throw new RuntimeException("Error in the f_get_users function: " + e.getSQLException().getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Authentication error: " + e.getMessage());
        }
    }
}