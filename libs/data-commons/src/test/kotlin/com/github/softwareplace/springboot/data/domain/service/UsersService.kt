package com.github.softwareplace.springboot.data.domain.service

import com.github.softwareplace.springboot.data.domain.model.AppUserData
import com.github.softwareplace.springboot.data.domain.repository.Spec
import com.github.softwareplace.springboot.data.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val repository: UserRepository
) {
    fun create(user: AppUserData): AppUserData {
        return repository.save(user)
    }

    fun findByEmail(email: String): AppUserData? {
        return repository.findByEmail(email)
    }

    fun findAll(): List<AppUserData> {
        return repository.findAll()
    }

    fun findAllCity(city: String): List<AppUserData> {
        val spec = Spec<AppUserData>()
            .andIsNotDeleted()
            .and { root, _, builder -> builder.equal(root.get<String>("address").get<String>("city"), city) }
            .build()

        return repository.findAll(spec)
    }

    fun saveAll(users: List<AppUserData>): List<AppUserData> {
        return repository.saveAll(users)
    }

}
