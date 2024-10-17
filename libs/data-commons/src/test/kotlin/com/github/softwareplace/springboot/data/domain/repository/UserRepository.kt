package com.github.softwareplace.springboot.data.domain.repository

import com.github.softwareplace.springboot.data.domain.model.AppUserData
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : BaseRepository<AppUserData, Long> {

    fun findByEmail(email: String): AppUserData?
}
