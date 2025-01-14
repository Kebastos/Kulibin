package com.kebastos.kulibin.models

import java.io.File

const val KULIBIN_DEFAULT_SCRIPT_NAME = "Default Name"
const val KULIBIN_DEFAULT_SCRIPT_PATH = "Default Path"

data class ScriptInfoModel(var name: String = KULIBIN_DEFAULT_SCRIPT_NAME, var path: String = KULIBIN_DEFAULT_SCRIPT_PATH) {
    fun isDefaultScript(): Boolean {
        return name == KULIBIN_DEFAULT_SCRIPT_NAME && path == KULIBIN_DEFAULT_SCRIPT_PATH
    }

    fun isCorrectInfo(): Boolean {
        val file = File(path)

        return !isDefaultScript() && file.exists()
    }

    override fun toString(): String {
        return "$name | $path"
    }
}
