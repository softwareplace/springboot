package com.github.softwareplace.springboot.data.domain

import com.github.softwareplace.springboot.data.domain.service.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan


@SpringBootTest(classes = [MainApp::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan
abstract class BaseTest {

    @LocalServerPort
    private var port: Long = 0


    @Autowired
    protected lateinit var service: UsersService
}
