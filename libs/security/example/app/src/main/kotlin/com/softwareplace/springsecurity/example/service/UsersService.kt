package com.softwareplace.springsecurity.example.service

import com.github.softwareplace.springboot.security.model.RequestUser
import com.github.softwareplace.springboot.security.model.UserData
import com.github.softwareplace.springboot.security.service.AuthorizationUserService
import com.github.softwareplace.springboot.security.service.JwtService.Companion.SCOPES
import com.softwareplace.springsecurity.example.model.AppUserData
import com.softwareplace.springsecurity.example.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val repository: UserRepository
) : AuthorizationUserService {

    override fun claims(httpServletRequest: HttpServletRequest, userData: UserData): Map<String, List<Any>> {
        return mapOf(SCOPES to listOf("user-data-access:read"))
    }

    fun addUser(user: AppUserData) = repository.save(user)

    override fun findUser(user: RequestUser) = repository.findByEmail(user.username)

    override fun findUser(authToken: String) = repository.findByToken(authToken)

    override fun loadUserByUsername(username: String) = repository.findByToken(username)
}
