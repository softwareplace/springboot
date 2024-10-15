package com.github.softwareplace.springboot.security.model

import com.github.softwareplace.springboot.security.filter.JWTAuthorizationFilter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

interface UserData : UserDetails {
    val roles get():List<String> = emptyList()

    open fun authToken() = ""
    override fun getAuthorities() = emptyList<SimpleGrantedAuthority>()
    override fun getPassword(): String? = null
    override fun getUsername(): String? = null
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}

val UserData.authoritiesRoles
    get() = roles.map { role: String -> SimpleGrantedAuthority("${JWTAuthorizationFilter.ROLE}$role") }

val UserData.toAuthorizationUser get() = User(username, password, authoritiesRoles)
