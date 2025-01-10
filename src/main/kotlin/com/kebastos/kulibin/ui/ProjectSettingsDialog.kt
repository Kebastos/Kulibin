package com.kebastos.kulibin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import com.kebastos.kulibin.models.ScriptInfoModel
import com.kebastos.kulibin.services.projectSettings.ProjectInfoModel
import javax.swing.JComponent
import kotlin.collections.toTypedArray

class ProjectSettingsDialog(
    project: Project,
    private val buildScripts: List<ScriptInfoModel>,
    private val runScripts: List<ScriptInfoModel>,
    private val initialState: ProjectInfoModel,
) : DialogWrapper(project) {
    private val buildScriptComboBox: ComboBox<ScriptInfoModel> =
        ComboBox(buildScripts.toTypedArray()).apply {
            selectedItem = initialState.buildScript
        }
    private val runScriptComboBox: ComboBox<ScriptInfoModel> =
        ComboBox(runScripts.toTypedArray()).apply {
            selectedItem = initialState.runScript
        }

    init {
        title = "Build&Run Settings"
        init()
    }

    override fun createCenterPanel(): JComponent {
        val panel: DialogPanel =
            panel {
                row("Build Script:") {
                    cell(buildScriptComboBox)
                }
                row("Run Script:") {
                    cell(runScriptComboBox)
                }
            }

        return panel
    }

    fun getSelectedBuildScript(): ScriptInfoModel {
        return buildScriptComboBox.selectedItem as ScriptInfoModel
    }

    fun getSelectedRunScript(): ScriptInfoModel {
        return runScriptComboBox.selectedItem as ScriptInfoModel
    }
}
