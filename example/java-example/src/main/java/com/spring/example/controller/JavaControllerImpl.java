package com.spring.example.controller;

import com.spring.example.model.BaseResponse;
import com.spring.example.model.BaseResponseMapper;
import com.spring.example.openapi.controller.ExampleApi;
import com.spring.example.openapi.model.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@RestController
@RequiredArgsConstructor
public class JavaControllerImpl implements ExampleApi {

    private final BaseResponseMapper mapper;

    @Override
    public ResponseEntity<BaseResponseDTO> exampleBuild() {
        var response = BaseResponse.builder()
                .message("Application is running")
                .success(true)
                .time(LocalTime.now())
                .date(LocalDate.now())
                .dateTime(LocalDateTime.now())
                .timestamp(System.currentTimeMillis())
                .build();
        return ResponseEntity.ok(mapper.parse(response));
    }
}
