package com.softwareplace.springsecurity.example.controller

import com.github.softwareplace.springboot.security.example.rest.model.UserRest
import com.softwareplace.springsecurity.example.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class UsersControllerImplTest : BaseTest() {

    @Test
    fun `must to return status code 403 when set invalid authorization header`() {
        val response = apiClient.get("fdsafds").execute()

        assertEquals(403, response.code())
    }

    @Test
    fun `must to create return on when user create`() {
        val user = UserRest(
            name = "Spring Example 02",
            cpf = "618.929.860-51",
            cnpj = "96.877.667/0001-46",
            email = "springutil+example05@email.com",
            password = "p@ssW0rd"
        )

        val response = apiClient.create(user).execute()

        assertEquals(201, response.code())
        assertNotNull(response.body())
        assertNotNull(response.body()?.userId)

        val responseR = apiClient.create(user).execute()

        println(responseR)

    }

    @Test
    fun `must to return an authorization response with created user`() {
        val user = UserRest(
            name = "Spring Example 02",
            cpf = "220.423.190-82",
            cnpj = "12.698.310/0001-00",
            email = "springutil+example06@email.com",
            password = "p@ssW0rd"
        )

        val response = apiClient.create(user).execute()

        assertEquals(201, response.code())

        getAuthorization("springutil+example06@email.com", "p@ssW0rd")
    }

    @Test
    fun `must to return user profile based on authorization token`() {
        val authorization = getAuthorization("springutil+example01@email.com", "xGKm&pFedts2")

        val detailResponse = apiClient.get(authorization).execute()

        assertEquals(200, detailResponse.code())

        assertEquals("springutil+example01@email.com", detailResponse.body()?.email)
    }
}
