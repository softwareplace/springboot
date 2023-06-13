package com.java.example.controller;

import com.java.example.model.BaseResponse;
import com.java.example.model.BaseResponseMapper;
import com.java.example.rest.controller.ExampleApi;
import com.java.example.rest.model.BaseResponseRest;
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
    public ResponseEntity<BaseResponseRest> exampleBuild() {
        var response = BaseResponse.builder()
                .message("[java-example] Application is running")
                .success(true)
                .time(LocalTime.now())
                .date(LocalDate.now())
                .dateTime(LocalDateTime.now())
                .timestamp(System.currentTimeMillis())
                .build();
        return ResponseEntity.ok(mapper.parse(response));
    }
}
