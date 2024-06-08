package com.example.shared

import com.example.shared.model.InMemoryUser
import com.softwareplace.springsecurity.model.RequestUser
import com.softwareplace.springsecurity.service.AuthorizationUserService
import org.springframework.stereotype.Service

@Service
class AuthorizationUserServiceImpl : AuthorizationUserService {

    override fun findUser(user: RequestUser) = InMemoryUser()

    override fun findUser(authToken: String) = InMemoryUser()

    override fun loadUserByUsername(username: String?) = InMemoryUser()
}
