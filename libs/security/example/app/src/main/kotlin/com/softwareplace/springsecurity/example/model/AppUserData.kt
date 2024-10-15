package com.softwareplace.springsecurity.example.model

import VARCHAR_100_COLUMN_DEFINITION
import VARCHAR_150_COLUMN_DEFINITION
import VARCHAR_20_COLUMN_DEFINITION
import VARCHAR_60_COLUMN_DEFINITION
import com.github.softwareplace.springboot.security.encrypt.Encrypt
import com.github.softwareplace.springboot.security.model.UserData
import com.github.softwareplace.springboot.security.validator.annotation.ValidCpfCnpj
import com.github.softwareplace.springboot.security.validator.annotation.ValidEmail
import com.github.softwareplace.springboot.security.validator.annotation.ValidPassword
import jakarta.persistence.*
import org.springframework.data.annotation.Transient
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "app_users")
data class AppUserData(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long = 0,
    @Column(columnDefinition = VARCHAR_100_COLUMN_DEFINITION, nullable = false)
    val name: String,
    @ValidEmail
    @Column(columnDefinition = VARCHAR_150_COLUMN_DEFINITION, nullable = false, unique = true)
    val email: String,
    @ValidCpfCnpj(acceptOnly = ValidCpfCnpj.Type.CPF, onErrorUseName = "Invalid CPF")
    @Column(columnDefinition = VARCHAR_20_COLUMN_DEFINITION, nullable = false, unique = true)
    val cpf: String,
    @ValidCpfCnpj(acceptOnly = ValidCpfCnpj.Type.CNPJ, onErrorUseName = "Invalid CNPJ")
    @Column(columnDefinition = VARCHAR_20_COLUMN_DEFINITION, nullable = false, unique = true)
    val cnpj: String,

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var createdAt: LocalDateTime? = null,

    @Column(name = "password", columnDefinition = VARCHAR_60_COLUMN_DEFINITION, nullable = false)
    private var pass: String,

    @Column(name = "token", columnDefinition = VARCHAR_60_COLUMN_DEFINITION, nullable = false)
    var token: String? = null
) : UserData {

    @Transient
    @ValidPassword(onErrorUseName = "invalidPassword")
    private var userPasswordValidate: String = pass

    @PrePersist
    fun beforeCreate() {
        val encrypt = Encrypt(pass)
        pass = encrypt.encodedPassword
        token = encrypt.authToken
        createdAt = LocalDateTime.now().atZone(TimeZone.getTimeZone("America/Sao_Paulo").toZoneId()).toLocalDateTime()
    }

    override fun getPassword(): String = pass

    override fun getUsername(): String = email

    override fun authToken(): String = token!!
}
