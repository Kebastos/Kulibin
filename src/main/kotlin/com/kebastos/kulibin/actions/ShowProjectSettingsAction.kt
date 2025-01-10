package com.kebastos.kulibin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.kebastos.kulibin.services.projectSettings.ProjectSettingsState
import com.kebastos.kulibin.ui.ProjectSettingsDialog

class ShowProjectSettingsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        if (project == null) {
            throw NullPointerException("project is null")
        }

        val stateService = project.service<ProjectSettingsState>()

        val projectInfo = stateService.state

        val dialog =
            ProjectSettingsDialog(
                project,
                projectInfo.buildScripts,
                projectInfo.runScripts,
                projectInfo,
            )

        if (dialog.showAndGet()) {
            var newProjectInfo = projectInfo.copy(buildScript = dialog.getSelectedBuildScript(), runScript = dialog.getSelectedRunScript())

            stateService.loadState(newProjectInfo)
        }
    }
}
