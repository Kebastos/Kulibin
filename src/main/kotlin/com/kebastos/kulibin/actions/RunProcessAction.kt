package com.kebastos.kulibin.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.kebastos.kulibin.services.process.ProcessManager
import com.kebastos.kulibin.services.process.ProcessService
import com.kebastos.kulibin.services.projectSettings.ProjectSettingsState
import com.kebastos.kulibin.settings.ApplicationSettingsState

abstract class RunProcessAction(private val scriptType: ScriptType) : AnAction() {
    private val settingsState = ApplicationManager.getApplication().getService(ApplicationSettingsState::class.java).state

    override fun update(e: AnActionEvent) {
        val processService = ProcessManager.getProcessService()
        e.presentation.isEnabled = processService == null || !processService.isRunning()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val state = project?.service<ProjectSettingsState>()?.state
        val scriptInfo = when (scriptType) {
            ScriptType.RUN -> state?.runScript
            ScriptType.BUILD -> state?.buildScript
        }

        if (project != null && scriptInfo != null) {
            val processService = ProcessService(project, scriptInfo, settingsState.terminalType, settingsState.consoleViewLevel)

            if (!ProcessManager.isProcessRunning()) {
                processService.execProcess()
                ProcessManager.setProcessService(processService)
            } else {
                println("Process is already running.")
            }
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    enum class ScriptType {
        RUN, BUILD
    }
}
