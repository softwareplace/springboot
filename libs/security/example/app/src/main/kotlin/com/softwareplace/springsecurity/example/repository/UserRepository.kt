package com.softwareplace.springsecurity.example.repository

import com.softwareplace.springsecurity.example.model.AppUserData
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<AppUserData, Long> {

    fun findByEmail(email: String): AppUserData?
    fun findByToken(token: String): AppUserData?
}
