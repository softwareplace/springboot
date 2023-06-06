package com.spring.example.model;


import com.spring.example.openapi.model.BaseResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseResponseMapper {

    BaseResponse parse(BaseResponseDTO input);

    BaseResponseDTO parse(BaseResponse input);
}
