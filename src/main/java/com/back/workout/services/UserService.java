package com.back.workout.services;

import com.back.workout.models.UserModel;
import com.back.workout.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login
    public UserModel login(String username, String password)  throws Exception {
        String query = "SELECT f_get_users(?, ?)";
        String result = jdbcTemplate.queryForObject(query, new Object[]{"username", username}, String.class);
        if (result == null) {
            return null;
        }
        UserModel user = objectMapper.readValue(result, UserModel.class);
        if(user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public void registerUser(UserModel userModel) {
        String encodePassword = passwordEncoder.encode(userModel.getPassword());
        try {
            String query = "CALL sp_register_user(?,?,?,?)";
            jdbcTemplate.update(query,
                    userModel.getId() != null ? userModel.getId() : null,
                    userModel.getUsername(),
                    userModel.getEmail(),
                    encodePassword
            );
        } catch (Exception e) {
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }
    }

    public Object getUsers(String type, String value) throws Exception {
        String query = "SELECT f_get_users(?, ?)";
        String result = jdbcTemplate.queryForObject(query, new Object[]{type, value}, String.class);

        if ("all".equals(type)) {
            return objectMapper.readValue(result, objectMapper.getTypeFactory().constructCollectionType(List.class, UserModel.class));
        } else if ("username".equals(type)) {
            return objectMapper.readValue(result, UserModel.class);
        } else if ("id".equals(type)) {
            return objectMapper.readValue(result, UserModel.class);
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

    }
}