package com.kebastos.kulibin.ui

import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.panel
import com.kebastos.kulibin.enums.ConsoleViewLevel
import com.kebastos.kulibin.enums.Terminal
import com.kebastos.kulibin.enums.getAvailableTerminals
import com.kebastos.kulibin.settings.ApplicationSettingsState

class KulibinSettingsPanel(settings: ApplicationSettingsState) {
    private val state = settings.state
    private val buildSearchMaskField = JBTextField(state.buildSearchMask)
    private val startSearchMaskField = JBTextField(state.startSearchMask)

    private val terminalTypeComboBox = ComboBox(getAvailableTerminals()).apply {
        selectedItem = state.terminalType
    }

    private val consoleViewLevel = ComboBox(ConsoleViewLevel.entries.toTypedArray()).apply {
        selectedItem = state.consoleViewLevel
    }

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
            row("Terminal Type:") {
                cell(terminalTypeComboBox)
                    .resizableColumn()
                    .align(Align.FILL)
            }
            row("Console View Level:") {
                cell(consoleViewLevel)
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
        terminalTypeComboBox.selectedItem = state.terminalType
        consoleViewLevel.selectedItem = state.consoleViewLevel
    }

    fun applyChanges() {
        state.buildSearchMask = buildSearchMaskField.text
        state.startSearchMask = startSearchMaskField.text
        state.terminalType = terminalTypeComboBox.selectedItem as Terminal
        state.consoleViewLevel = consoleViewLevel.selectedItem as ConsoleViewLevel
    }

    fun isModified(): Boolean {
        return buildSearchMaskField.text != state.buildSearchMask ||
                startSearchMaskField.text != state.startSearchMask ||
                terminalTypeComboBox.selectedItem != state.terminalType ||
                consoleViewLevel.selectedItem!= state.consoleViewLevel
    }
}