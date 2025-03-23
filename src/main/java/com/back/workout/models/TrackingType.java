package com.back.workout.models;

import lombok.Getter;

@Getter
public enum TrackingType {
    SET_REPS("set_reps"),
    DURATION("duration");

    private final String value;

    TrackingType(String value) {
        this.value = value;
    }

}