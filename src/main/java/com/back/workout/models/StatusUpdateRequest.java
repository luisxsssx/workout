package com.back.workout.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusUpdateRequest {
    private String table;
    private Integer[] ids;
    private String status;
}
