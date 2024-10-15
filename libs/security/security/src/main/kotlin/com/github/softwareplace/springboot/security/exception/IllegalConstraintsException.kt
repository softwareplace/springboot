package com.github.softwareplace.springboot.security.exception

class IllegalConstraintsException(message: String?, val errors: Map<String, List<String>>) : Exception(message)
