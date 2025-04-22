package com.back.workout.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    public Integer id;
    public String username;
    public String email;
    public String password;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}