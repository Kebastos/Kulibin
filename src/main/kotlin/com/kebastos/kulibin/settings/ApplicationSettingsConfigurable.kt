package com.kebastos.kulibin.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.kebastos.kulibin.ui.KulibinSettingsPanel
import javax.swing.JComponent

class ApplicationSettingsConfigurable(private val project: Project) : Configurable {
    private var panel: KulibinSettingsPanel? = null
    private val state = ApplicationManager.getApplication().getService(ApplicationSettingsState::class.java)

    override fun createComponent(): JComponent? {
        panel = KulibinSettingsPanel(state)
        return panel?.panel
    }

    override fun isModified(): Boolean {
        return panel?.isModified() == true
    }

    override fun apply() {
        panel?.applyChanges()
    }

    override fun reset() {
        panel?.reset()
    }

    override fun getDisplayName(): String {
        return "Kulibin"
    }
}
