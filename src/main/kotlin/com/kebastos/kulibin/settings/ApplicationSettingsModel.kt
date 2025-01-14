package com.kebastos.kulibin.settings

import com.kebastos.kulibin.enums.ConsoleViewLevel
import com.kebastos.kulibin.enums.Terminal
import com.kebastos.kulibin.enums.getDefaultTerminal

data class ApplicationSettingsModel(
    var buildSearchMask: String = "*rebuild*.bat",
    var startSearchMask: String = "*start*.bat",
    var terminalType: Terminal = getDefaultTerminal(),
    var consoleViewLevel: ConsoleViewLevel = ConsoleViewLevel.DEBUG
)