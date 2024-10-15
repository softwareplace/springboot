package com.softwareplace.springsecurity.example.factory

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule

class ObjectMapperFactory {
    companion object {
        fun factory(): ObjectMapper {
            return ObjectMapper()
                .registerModule(
                    KotlinModule.Builder()
                        .configure(KotlinFeature.NullToEmptyCollection, false)
                        .configure(KotlinFeature.NullToEmptyMap, false)
                        .configure(KotlinFeature.NullIsSameAsDefault, true)
                        .configure(KotlinFeature.SingletonSupport, true)
                        .configure(KotlinFeature.StrictNullChecks, true)
                        .build()
                )
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(JavaTimeModule())
        }
    }
}
