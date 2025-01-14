package com.kebastos.kulibin.enums

import com.intellij.execution.ui.ConsoleViewContentType

enum class ConsoleViewLevel(val contentType: ConsoleViewContentType) {
    INFO(ConsoleViewContentType.NORMAL_OUTPUT),
    ERROR(ConsoleViewContentType.ERROR_OUTPUT),
    WARNING(ConsoleViewContentType.SYSTEM_OUTPUT),
    SYSTEM(ConsoleViewContentType.SYSTEM_OUTPUT),
    DEBUG(ConsoleViewContentType.SYSTEM_OUTPUT)
}