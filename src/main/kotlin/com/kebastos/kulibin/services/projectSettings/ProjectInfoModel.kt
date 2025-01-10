package com.kebastos.kulibin.services.projectSettings

import com.kebastos.kulibin.models.ScriptInfoModel

data class ProjectInfoModel(
    var buildScripts: List<ScriptInfoModel> = listOf(),
    var runScripts: List<ScriptInfoModel> = listOf(),
    var buildScript: ScriptInfoModel = ScriptInfoModel(),
    var runScript: ScriptInfoModel = ScriptInfoModel(),
)
