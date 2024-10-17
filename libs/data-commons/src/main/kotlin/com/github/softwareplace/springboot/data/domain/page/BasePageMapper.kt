package com.github.softwareplace.springboot.data.domain.page

import org.mapstruct.Mapping
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity

@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@Mapping(source = "first", target = "isFirst")
@Mapping(source = "last", target = "isLast")
@Mapping(expression = "java(input.hasNext())", target = "hasNext")
@Mapping(expression = "java(input.hasPrevious())", target = "hasPrevious")
@Mapping(expression = "java(input.hasContent())", target = "hasContent")
@Mapping(expression = "java(input.getSize())", target = "propertySize")
@Mapping(expression = "java(input.getTotalPages())", target = "totalPages")
@Mapping(target = "content", expression = "java(contentMap(input.getContent()))")
annotation class PageMapping

interface BasePageMapper<T, R, PR> : BaseMapper<T, R> {
    @PageMapping
    fun parse(input: Page<T>): PR
}

fun <I, R, PR, T : BasePageMapper<I, R, PR>> T.ok(input: Page<I>) = ResponseEntity.ok(parse(input))

fun <I, R, PR, T : BasePageMapper<I, R, PR>> T.ok(input: I) = ResponseEntity.ok(parse(input))
