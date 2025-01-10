package com.kebastos.kulibin.services.projectSettings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.kebastos.kulibin.models.ScriptInfoModel
import com.kebastos.kulibin.settings.ApplicationSettingsState
import java.io.File

class ProjectSettingsLoaderService : ProjectActivity {
    private val logger = Logger.getInstance(ProjectSettingsLoaderService::class.java)
    private val settingsState = ApplicationManager.getApplication().getService(ApplicationSettingsState::class.java).state

    override suspend fun execute(project: Project) {
        loadSettings(project)
    }

    private fun loadSettings(project: Project) {
        val projectState = project.getService(ProjectSettingsState::class.java)
        val rootPath = project.basePath ?: return
        var buildScripts: List<ScriptInfoModel> = listOf()
        var runScripts: List<ScriptInfoModel> = listOf()

        if (settingsState.buildSearchMask.isNotEmpty()) {
            buildScripts = findFilesByMask(rootPath, settingsState.buildSearchMask)
        } else {
            logger.warn("Файлы запуска сборки не найдены.")
        }

        if (settingsState.startSearchMask.isNotEmpty()) {
            runScripts = findFilesByMask(rootPath, settingsState.startSearchMask)
        } else {
            logger.warn("Файлы запуска сборки не найдены.")
        }

        val firstBuildScript = buildScripts.getOrNull(0) ?: ScriptInfoModel()
        val firstRunScript = runScripts.getOrNull(0) ?: ScriptInfoModel()

        projectState.loadState(ProjectInfoModel(buildScripts, runScripts, firstBuildScript, firstRunScript))
    }

    private fun findFilesByMask(
        rootPath: String,
        mask: String,
    ): List<ScriptInfoModel> {
        val foundFiles = mutableListOf<ScriptInfoModel>()
        var currentDir = File(rootPath)

        val regex =
            mask
                .replace(".", "\\.")
                .replace("*", ".*")
                .toRegex()

        while (currentDir.exists()) {
            currentDir.listFiles()?.forEach { file ->
                if (file.isFile && file.name.matches(regex)) {
                    foundFiles.add(ScriptInfoModel(file.name, file.absolutePath))
                }
            }

            currentDir = currentDir.parentFile ?: break
        }

        return foundFiles
    }
}
