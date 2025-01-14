package com.kebastos.kulibin.enums

import com.intellij.execution.configurations.GeneralCommandLine
import java.io.File
import java.nio.charset.Charset
import java.util.*

enum class Terminal(val command: String, val options: String) {
    CMD("cmd.exe", "/c"),
    POWERSHELL("powershell.exe", "-Command"),
    SH("sh", "-c");

    fun getCommandLine(scriptFile: File): GeneralCommandLine {
        return GeneralCommandLine(command, options, scriptFile.path).apply {
            workDirectory = scriptFile.parentFile
            charset = Charset.forName("UTF-8")
        }
    }
}

fun getDefaultTerminal(): Terminal {
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
    return when {
        osName.contains("win") -> Terminal.POWERSHELL
        osName.contains("nix") || osName.contains("nux") || osName.contains("mac") -> Terminal.SH
        else -> Terminal.SH
    }
}

fun getAvailableTerminals(): Array<Terminal> {
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
    return when {
        osName.contains("win") -> arrayOf(Terminal.POWERSHELL, Terminal.CMD)
        osName.contains("nix") || osName.contains("nux") || osName.contains("mac") -> arrayOf(Terminal.SH)
        else -> arrayOf(Terminal.SH)
    }
}