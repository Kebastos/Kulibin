package com.kebastos.kulibin.services.logger

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project

class KulibinLogger private constructor() {
    companion object {
        private var logger: Logger? = null

        fun initLogger(project: Project) {
            logger = Logger.getInstance(project.javaClass)
        }

        fun logMessage(message: String) {
            logger?.info(message) ?: println("Logger not initialized. Message: $message")
        }
    }
}
