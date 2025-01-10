package com.kebastos.kulibin.ui

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.panel
import com.kebastos.kulibin.settings.ApplicationSettingsState

class KulibinSettingsPanel(settings: ApplicationSettingsState) {
    private val state = settings.state
    private val buildSearchMaskField = JBTextField(state.buildSearchMask)
    private val startSearchMaskField = JBTextField(state.startSearchMask)

    val panel: DialogPanel =
        panel {
            row("Build Search Mask:") {
                cell(buildSearchMaskField)
                    .resizableColumn()
                    .align(Align.FILL)
            }
            row("Start Search Mask:") {
                cell(startSearchMaskField)
                    .resizableColumn()
                    .align(Align.FILL)
            }
        }

    init {
        reset()
    }

    fun reset() {
        buildSearchMaskField.text = state.buildSearchMask
        startSearchMaskField.text = state.startSearchMask
    }

    fun applyChanges() {
        state.buildSearchMask = buildSearchMaskField.text
        state.startSearchMask = startSearchMaskField.text
    }

    fun isModified(): Boolean {
        return buildSearchMaskField.text != state.buildSearchMask || startSearchMaskField.text != state.startSearchMask
    }
}
