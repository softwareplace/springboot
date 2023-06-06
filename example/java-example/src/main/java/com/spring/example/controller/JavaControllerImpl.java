package com.spring.example.controller;

import com.spring.example.openapi.controller.ExampleApi;
import com.spring.example.openapi.model.BaseResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaControllerImpl implements ExampleApi {

    @Override
    public ResponseEntity<BaseResponseDTO> exampleBuild() {
        var response = new BaseResponseDTO();
        response.setMessage("Application is running");
        response.setSuccess(true);
        response.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
}
