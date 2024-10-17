package com.github.softwareplace.springboot.data.domain.model

import com.github.softwareplace.springboot.data.domain.sql.ApplicationConstants.VARCHAR_100_COLUMN_DEFINITION
import com.github.softwareplace.springboot.data.domain.sql.ApplicationConstants.VARCHAR_150_COLUMN_DEFINITION
import com.github.softwareplace.springboot.data.domain.sql.ApplicationConstants.VARCHAR_50_COLUMN_DEFINITION
import com.github.softwareplace.springboot.data.domain.sql.ApplicationConstants.VARCHAR_60_COLUMN_DEFINITION
import jakarta.persistence.*


@Entity
@Table(name = "app_users")
data class AppUserData(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long? = null,

    @Column(columnDefinition = VARCHAR_100_COLUMN_DEFINITION, nullable = false)
    val name: String,

    @Column(columnDefinition = VARCHAR_150_COLUMN_DEFINITION, nullable = false, unique = true)
    val email: String,

    @Column(columnDefinition = VARCHAR_50_COLUMN_DEFINITION, nullable = false, unique = false)
    val accountStatus: String,

    @Column(name = "password", columnDefinition = VARCHAR_60_COLUMN_DEFINITION, nullable = false)
    private var pass: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id")
    val address: Address
) : BaseEntity()

@Entity
@Table(name = "users_address")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var addressId: Long? = null,
    @Column(columnDefinition = VARCHAR_150_COLUMN_DEFINITION, nullable = false, unique = false)
    val street: String,
    @Column(columnDefinition = VARCHAR_50_COLUMN_DEFINITION, nullable = false, unique = false)
    val number: String,
    @Column(columnDefinition = VARCHAR_150_COLUMN_DEFINITION, nullable = false, unique = false)
    val city: String,
    @Column(columnDefinition = VARCHAR_100_COLUMN_DEFINITION, nullable = false, unique = false)
    val state: String,
    @Column(columnDefinition = VARCHAR_100_COLUMN_DEFINITION, nullable = false, unique = false)
    val country: String,
    @Column(columnDefinition = VARCHAR_50_COLUMN_DEFINITION, nullable = false, unique = false)
    val zipCode: String
) : BaseEntity()
