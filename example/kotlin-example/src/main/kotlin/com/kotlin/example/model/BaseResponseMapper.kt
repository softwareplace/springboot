package com.kotlin.example.model

import com.kotlin.example.openapi.rest.model.BaseResponseRest
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface BaseResponseMapper {
    fun parse(input: BaseResponseRest): BaseResponse
    fun parse(input: BaseResponse): BaseResponseRest
}
