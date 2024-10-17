package com.github.softwareplace.springboot.data.domain.page

interface BaseMapper<T, R> {
    fun parse(input: T): R

    fun from(input: R): T

    fun parse(input: List<T>): List<R>

    fun contentMap(input: List<T>): List<R> {
        if (input.isEmpty()) {
            return emptyList()
        }
        return parse(input)
    }
}
