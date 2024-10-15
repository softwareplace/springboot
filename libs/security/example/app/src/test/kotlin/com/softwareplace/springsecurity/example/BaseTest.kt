package com.softwareplace.springsecurity.example

import com.github.softwareplace.springboot.security.SecurityModule
import com.softwareplace.springsecurity.example.client.ApiClient
import com.softwareplace.springsecurity.example.factory.ObjectMapperFactory
import com.softwareplace.springsecurity.example.model.AppUserData
import com.softwareplace.springsecurity.example.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import retrofit2.Retrofit
import retrofit2.create


//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(initializers = [DatabaseConnection.Initializer::class])
@SpringBootTest(classes = [MainApp::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackageClasses = [SecurityModule::class])
abstract class BaseTest {

    @LocalServerPort
    private var port: Long = 0

    @Autowired
    protected lateinit var builder: Retrofit.Builder

    @Autowired
    protected lateinit var repository: UserRepository

    val baseApiUrl: String by lazy { "http://127.0.0.1:${port}" }

    protected val apiClient: ApiClient by lazy {
        builder
            .baseUrl(baseApiUrl)
            .build()
            .create()
    }

    @PostConstruct
    fun onStarted() {
        repository.deleteAll()
        val stream = javaClass.classLoader.getResourceAsStream("user.json")
        ObjectMapperFactory.factory()
            .readValue(stream, AppUserData::class.java)
            .let(repository::save)
    }

    fun getAuthorization(email: String, pass: String): String {
        val authorizationRequest = ApiClient.AuthorizationRequest(email, pass)
        val authorization = apiClient.authorization(authorizationRequest).execute()

        assertEquals(200, authorization.code())

        val authorizationRest = authorization.body()
        assertNotNull(authorizationRest)
        return authorizationRest!!.jwt
    }
}
