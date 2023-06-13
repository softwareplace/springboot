package com.java.example.model;


import com.java.example.rest.model.BaseResponseRest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseResponseMapper {

    BaseResponse parse(BaseResponseRest input);

    BaseResponseRest parse(BaseResponse input);
}
