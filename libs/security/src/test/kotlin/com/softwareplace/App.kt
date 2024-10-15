package com.softwareplace

import com.github.softwareplace.springboot.security.authorization.AuthorizationHandler
import com.github.softwareplace.springboot.security.model.RequestUser
import com.github.softwareplace.springboot.security.model.UserData
import com.github.softwareplace.springboot.security.service.AuthorizationUserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService


@SpringBootApplication
@ComponentScan(basePackageClasses = [EnableSecurityModule::class])
class App {

    @Bean
    fun userDetailsService() = UserDetailsService { null }

    @Bean
    fun authorizationUserService() = object : AuthorizationUserService {
        override fun findUser(user: RequestUser): UserData? {
            return null
        }

        override fun findUser(authToken: String): UserData? {
            return null
        }

        override fun loadUserByUsername(username: String?): UserDetails {
            TODO("Not yet implemented")
        }
    }

    @Bean
    fun authorizationHandler() = object : AuthorizationHandler {
        override fun authorizationSuccessfully(request: HttpServletRequest, userData: UserData) {
            TODO("Not yet implemented")
        }

        override fun onAuthorizationFailed(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain,
            exception: Exception
        ) {
            TODO("Not yet implemented")
        }

        override fun onAuthorizationFailed(
            request: HttpServletRequest,
            response: HttpServletResponse
        ) {
            TODO("Not yet implemented")
        }


    }

//    @Bean
//    @Primary
//    fun controllerAdvice() = ControllerExceptionAdvice(getObjectMapper(), applicationInfo = ApplicationInfo())
}
