package com.github.softwareplace.springboot.data.domain.page

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

data class QueryParam(
    val page: Int,
    val number: Int,
    val sortBy: List<String>? = arrayListOf(),
    val direction: Direction = Direction.DESC,
    val deleted: Boolean? = null,
    val name: List<String>? = null,
    val status: List<String>? = null
) {
    fun toPageable(): PageRequest {
        return if (!sortBy.isNullOrEmpty()) {
            PageRequest.of(page, number, direction.toDirection(Sort.by(*sortBy.toTypedArray())))
        } else PageRequest.of(page, number)
    }

    private fun Direction.toDirection(sort: Sort): Sort {
        return when (this) {
            Direction.DESC,
            Direction.desc -> sort.descending()

            else -> sort.ascending()
        }
    }

    enum class Direction(val value: String) {
        @JsonProperty("asc")
        asc("asc"),

        @JsonProperty("ASC")
        ASC("ASC"),

        @JsonProperty("desc")
        desc("desc"),

        @JsonProperty("DESC")
        DESC("DESC");

        companion object {
            fun from(value: String) = entries.firstOrNull { it.value.equals(value, true) }
        }
    }
}
