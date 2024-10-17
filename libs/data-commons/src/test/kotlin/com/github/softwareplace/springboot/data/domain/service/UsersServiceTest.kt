package com.github.softwareplace.springboot.data.domain.service

import com.github.softwareplace.springboot.data.domain.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class UsersServiceTest : BaseTest() {

    @Test
    fun `should return all users`() {
        val users = service.findAll()
        assert(users.isNotEmpty())
    }

    @Test
    fun `should return user by city name`() {
        val allUsers = service.findAll()
        assert(allUsers.isNotEmpty())

        val firstUserCity = allUsers.first().address.city

        val allMatchingCity = allUsers.filter { it.address.city == firstUserCity }

        val users = service.findAllCity(firstUserCity)

        assert(users.isNotEmpty())

        assertEquals(allMatchingCity.size, users.size)
    }
}
