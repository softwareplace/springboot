package com.spring.example.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class BaseResponse {
    private Boolean success;

    private Long timestamp;

    private String message;
    private LocalDate date;
    private LocalDateTime dateTime;
    private LocalTime time;
}
