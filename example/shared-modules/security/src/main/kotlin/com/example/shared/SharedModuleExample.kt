package com.example.shared

import com.softwareplace.jsonlogger.log.JsonLog
import org.slf4j.Logger
import org.slf4j.event.Level
import java.time.LocalDateTime

object SharedModuleExample {

    fun test(source: String, logger: Logger) {
        JsonLog(kLog = logger)
            .level(Level.INFO)
            .message("$source - is working")
            .add("dateTime", LocalDateTime.now())
            .run()
    }
}
