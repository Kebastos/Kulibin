package com.kebastos.kulibin.settings

data class ApplicationSettingsModel(
    var buildSearchMask: String = "*rebuild*.bat",
    var startSearchMask: String = "*start*.bat",
)
