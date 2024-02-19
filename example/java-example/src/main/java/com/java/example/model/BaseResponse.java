package com.java.example.model;


import com.example.shared.model.UserData;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record BaseResponse(
        Boolean success,
        Long timestamp,
        String message,
        LocalDate date,
        LocalDateTime dateTime,
        LocalTime time,
        UserData userData) {

}
