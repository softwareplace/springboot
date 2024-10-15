package com.softwareplace.springsecurity.example.handler

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.softwareplace.jsonlogger.log.jsonLog
import com.softwareplace.jsonlogger.log.kLogger
import com.softwareplace.springsecurity.example.error.GatewayException
import com.softwareplace.springsecurity.example.factory.ObjectMapperFactory
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Response

@Component
class GatewayCallHandler {

    private val mapper: ObjectMapper by lazy {
        ObjectMapperFactory.factory()
    }

    @Throws(GatewayException::class)
    fun <T> from(caller: Call<T>): T? {
        try {
            val response = caller.execute()
            if (response.isSuccessful && response.body() != null) {
                return response.body()
            }
            throw GatewayException(getError(response))
        } catch (error: Exception) {
            val errorData = if (error is GatewayException) {
                error.errorMap
            } else {
                emptyMap()
            }

            kLogger.jsonLog
                .error(error)
                .add("errorData", errorData)
                .message(error.message)
                .printStackTrackerEnable()
                .run()

            throw GatewayException("Request failed")
        }
    }

    private fun getError(response: Response<*>): Map<String, Any> {
        return try {
            mapper.readValue(response.errorBody()!!.string(), errorType())
        } catch (error: Exception) {
            java.util.Map.ofEntries<String, Any?>(java.util.Map.entry("error", error.message))
        }
    }

    private fun errorType(): TypeReference<Map<String, Any>> {
        return object : TypeReference<Map<String, Any>>() {}
    }
}
