package com.example.shared

import com.example.shared.model.InMemoryUser
import com.github.softwareplace.springboot.security.model.RequestUser
import com.github.softwareplace.springboot.security.service.AuthorizationUserService
import org.springframework.stereotype.Service

@Service
class AuthorizationUserServiceImpl : AuthorizationUserService {

    override fun findUser(user: RequestUser) = InMemoryUser()

    override fun findUser(authToken: String) = InMemoryUser()

    override fun loadUserByUsername(username: String?) = InMemoryUser()
}
