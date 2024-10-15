package com.softwareplace.springsecurity.example.error

class GatewayException : RuntimeException {
    val errorMap: Map<String, Any>?

    constructor(errorMap: Map<String, Any>) {
        this.errorMap = errorMap
    }

    constructor(message: String) : super(message) {
        errorMap = null
    }
}
