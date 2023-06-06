package com.spring.example.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BaseResponse {
    private Boolean success;

    private Long timestamp;

    private String message;
}
