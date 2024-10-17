package com.github.softwareplace.springboot.data.domain.repository

import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

class Spec<T> {

    private var specification: Specification<T>? = null

    private fun add(specification: Specification<T>, next: (Specification<T>) -> Specification<T>) {
        if (this.specification == null) {
            this.specification = specification
        } else {
            this.specification = next.invoke(this.specification!!)
        }
    }

    private fun and(specification: Specification<T>) = add(specification) { it.and(specification) }

    private fun or(specification: Specification<T>) = add(specification) { it.or(specification) }

    fun <V> andOr(values: Map<String, V>): Spec<T> {
        and { root, _, builder ->
            builder.or(*toPredicate(values, builder, root))
        }
        return this
    }

    fun <V> andEquals(name: String, value: V?): Spec<T> {
        value?.let {
            and { root, _, builder -> builder.equal(root.get<V>(name), value) }
        }
        return this
    }

    fun <V> orEquals(name: String, value: V?): Spec<T> {
        value?.let {
            or { root, _, builder -> builder.equal(root.get<V>(name), value) }
        }
        return this
    }

    fun <V> andDifferent(name: String, value: V?): Spec<T> {
        value?.let {
            and { root, _, builder -> builder.notEqual(root.get<V>(name), value) }
        }
        return this
    }

    fun <V> orDifferent(name: String, value: V?): Spec<T> {
        value?.let {
            or { root, _, builder -> builder.notEqual(root.get<V>(name), value) }
        }
        return this
    }

    fun andGreaterThanOrEqualTo(name: String, value: LocalDate?): Spec<T> {
        value?.let {
            and { root, _, builder -> builder.greaterThanOrEqualTo(root.get(name), value) }
        }
        return this
    }

    fun orGreaterThanOrEqualTo(name: String, value: LocalDate?): Spec<T> {
        value?.let {
            or { root, _, builder -> builder.greaterThanOrEqualTo(root.get(name), value) }
        }
        return this
    }

    fun andLessThanOrEqualTo(name: String, value: LocalDate?): Spec<T> {
        value?.let {
            and { root, _, builder -> builder.lessThanOrEqualTo(root.get(name), value) }
        }
        return this
    }

    fun andBetween(name: String, first: LocalDate, second: LocalDate): Spec<T> {
        and { root, _, builder -> builder.between(root.get(name), first, second) }
        return this
    }

    fun ordBetween(name: String, first: LocalDate, second: LocalDate): Spec<T> {
        or { root, _, builder -> builder.between(root.get<LocalDate>(name), first, second) }
        return this
    }

    fun orLessThanOrEqualTo(name: String, value: LocalDate?): Spec<T> {
        value?.let {
            or { root, _, builder -> builder.lessThanOrEqualTo(root.get(name), value) }
        }
        return this
    }


    fun <V> andNotEmpty(name: String): Spec<T> {
        and { root, _, builder -> builder.notEqual(root.get<V>(name), "") }
        return this
    }

    fun andIsNotDeleted(): Spec<T> {
        and { root, _, builder -> builder.isNull(root.get<Boolean>("deletedAt")) }
        return this
    }

    fun andIsNull(name: String): Spec<T> {
        and { root, _, builder -> builder.isNull(root.get<Boolean>(name)) }
        return this
    }

    fun andIsNotNull(name: String): Spec<T> {
        and { root, _, builder -> builder.isNotNull(root.get<Boolean>(name)) }
        return this
    }

    fun orIsNotNull(name: String): Spec<T> {
        or { root, _, builder -> builder.isNotNull(root.get<Boolean>(name)) }
        return this
    }

    fun andLike(name: String, pattern: String?): Spec<T> {
        pattern?.let {
            and { root, _, builder -> builder.like(builder.upper(root.get(name)), it.uppercase()) }
        }
        return this
    }

    fun orLike(name: String, pattern: String?): Spec<T> {
        pattern?.let {
            or { root, _, builder -> builder.like(builder.upper(root.get(name)), it.uppercase()) }
        }
        return this
    }

    fun andLike(name: String, patterns: List<String>?): Spec<T> {
        patterns?.let {
            it.forEach { and { root, _, builder -> builder.like(builder.upper(root.get(name)), it.uppercase()) } }
        }
        return this
    }

    fun orLike(name: String, patterns: List<String>?): Spec<T> {
        patterns?.let {
            it.forEach { and { root, _, builder -> builder.like(builder.upper(root.get(name)), it.uppercase()) } }
        }
        return this
    }

    fun orLikeIn(name: String, patterns: List<String>?): Spec<T> {
        patterns?.let {
            if (patterns.isNotEmpty()) {
                and { root, _, builder -> builder.or(*toLikePredicates(it, builder, root, name)) }
            }
        }
        return this
    }

    fun and(spec: Specification<T>?): Spec<T> {
        spec?.let { and(it) }
        return this
    }

    fun or(spec: Specification<T>?): Spec<T> {
        spec?.let { or(it) }
        return this
    }

    fun or(spec: Spec<T>): Spec<T> {
        or(spec.build())
        return this
    }

    fun <V> andIn(name: String, values: List<V>?): Spec<T> {
        values?.let {
            and { root, _, _ -> root.get<V>(name).`in`(values) }
        }
        return this
    }

    fun <V> orIn(name: String, values: List<V>?): Spec<T> {
        values?.let {
            or { root, _, _ -> root.get<V>(name).`in`(values) }
        }
        return this
    }

    fun <V> andNotIn(name: String, values: List<V>?): Spec<T> {
        values?.let {
            and { root, _, builder -> builder.not(root.get<V>(name).`in`(values)) }
        }
        return this
    }

    fun orLikeIn(name: String, specTarget: SpecTarget<T, *>, patterns: List<String>?): Spec<T> {
        patterns?.let {
            if (patterns.isNotEmpty()) {
                and { root, _, builder -> builder.or(*toLikePredicates(it, specTarget, builder, root, name)) }
            }
        }
        return this
    }

    fun andLike(name: String, specTarget: SpecTarget<T, *>, values: String?): Spec<T> {
        val andLike: Specification<T> = object : Specification<T> {
            override fun toPredicate(
                root: Root<T>,
                query: CriteriaQuery<*>?,
                builder: CriteriaBuilder
            ): Predicate? {
                val path = specTarget.get(root)

                values?.let {
                    return builder.like(builder.upper(path.get(name)), it.uppercase())
                }
                return builder.conjunction()
            }
        }

        and(andLike)

        return this
    }

    fun build(): Specification<T> =
        specification ?: Specification<T> { _, _, builder -> builder.conjunction() }

    companion object {
        fun <T> of(): Spec<T> = Spec()
    }

}

fun <T> toLikePredicates(
    patterns: List<String>,
    builder: CriteriaBuilder,
    root: Root<T>,
    name: String
): Array<Predicate> = patterns.map { pattern ->
    builder.like(builder.upper(root.get(name)), pattern.uppercase())
}.toTypedArray()

fun <T, V> toPredicate(
    values: Map<String, V>,
    builder: CriteriaBuilder,
    root: Root<T>
): Array<Predicate> = values.entries.map { vale ->
    builder.equal(root.get<V>(vale.key), vale.value)
}.toTypedArray()

fun <T> toLikePredicates(
    patterns: List<String>,
    specTarget: SpecTarget<T, *>,
    builder: CriteriaBuilder,
    root: Root<T>,
    name: String
): Array<Predicate> = patterns.map { pattern ->
    val path = specTarget.get(root)
    val pathValue = builder.upper(path.get(name))
    builder.like(pathValue, pattern.uppercase())
}.toTypedArray()

fun List<String>.toQueryLike() = map { "%$it%" }

data class SpecTarget<T, S>(
    private val value: String,
    private val next: SpecTarget<T, *>? = null
) {
    fun get(root: Root<T>): Path<*> {
        val path = root.get<S>(value)

        if (next != null) {
            return next.get(path)
        }
        return path
    }

    fun get(path: Path<*>): Path<*> {
        val currentPath = path.get<S>(value)
        if (next != null) {
            return next.get(currentPath)
        }
        return currentPath
    }
}
