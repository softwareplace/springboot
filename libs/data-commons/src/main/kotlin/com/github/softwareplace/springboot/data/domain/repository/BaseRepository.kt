package com.github.softwareplace.springboot.data.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface BaseRepository<T, ID> : JpaRepository<T, ID>,
    JpaSpecificationExecutor<T> {
}

object RepositoryUtils {
    fun <T> BaseRepository<T, *>.findAllBy(spec: Spec<T>?, pageRequest: Pageable): Page<T> {
        val specification = spec?.build()
        return if (specification != null) {
            findAll(specification, pageRequest)
        } else {
            findAll(pageRequest)
        }
    }

    fun <T> BaseRepository<T, *>.findOneBy(spec: Spec<T>?): Optional<out T> {
        val specification = spec?.build()
        return if (specification != null) {
            findOne(specification)
        } else {
            Optional.empty<T>()
        }
    }

    fun <T : Any> BaseRepository<T, *>.findOneBySpec(spec: Spec<T>?): Optional<T> {
        val specification = spec?.build()
        return if (specification != null) {
            findOne(specification)
        } else {
            Optional.empty()
        }
    }
}
