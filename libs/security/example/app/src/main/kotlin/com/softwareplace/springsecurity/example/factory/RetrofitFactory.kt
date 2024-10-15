package com.softwareplace.springsecurity.example.factory

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration()
class RetrofitFactory {

    private val mapper: ObjectMapper by lazy {
        ObjectMapperFactory.factory()
    }

    @Bean
    fun retrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create(mapper))
    }
}
