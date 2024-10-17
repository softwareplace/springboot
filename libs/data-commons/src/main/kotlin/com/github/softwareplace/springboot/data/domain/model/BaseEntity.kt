package com.github.softwareplace.springboot.data.domain.model

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PreUpdate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.*

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@DiscriminatorValue(value = "true")
abstract class BaseEntity(

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val createdAt: LocalDateTime = LocalDateTime.now()
        .atZone(TimeZone.getDefault().toZoneId())
        .toLocalDateTime(),

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        .atZone(TimeZone.getDefault().toZoneId())
        .toLocalDateTime(),

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var deletedAt: LocalDateTime? = null,
) {

    @PreUpdate
    open fun beforeUpdate() {
        updatedAt = LocalDateTime.now()
            .atZone(TimeZone.getDefault().toZoneId())
            .toLocalDateTime()
        beforeUpdateHandler()
    }

    open fun beforeUpdateHandler() {

    }

    open fun asDeleted() {
        deletedAt = LocalDateTime.now()
            .atZone(TimeZone.getDefault().toZoneId())
            .toLocalDateTime()
    }
}
