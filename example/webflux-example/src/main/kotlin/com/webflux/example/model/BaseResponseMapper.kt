package com.webflux.example.model

import com.webflux.example.rest.model.BaseResponseRest
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface BaseResponseMapper {
    fun parse(input: BaseResponseRest): BaseResponse
    fun parse(input: BaseResponse): BaseResponseRest
}
