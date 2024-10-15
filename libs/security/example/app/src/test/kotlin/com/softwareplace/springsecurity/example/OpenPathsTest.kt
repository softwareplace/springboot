package com.softwareplace.springsecurity.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OpenPathsTest : BaseTest() {

    @Test
    fun `must to return status code 200 by accessing swagger doc path`() {
        val response = apiClient.swaggerDoc().execute()

        assertEquals(200, response.code())
    }

    @Test
    fun `must to return status code 200 by accessing api v3 doc path`() {
        val response = apiClient.v3ApiDoc().execute()

        assertEquals(200, response.code())
    }
}
