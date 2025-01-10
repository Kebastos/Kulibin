package com.kebastos.kulibin.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.kebastos.kulibin.services.process.ProcessManager

class StopAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val processService = ProcessManager.getProcessService()
        e.presentation.isEnabled = processService != null && processService.isRunning()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val processService = ProcessManager.getProcessService()

        if (processService != null && processService.isRunning()) {
            processService.stopProcess()
        } else {
            println("No process is currently running.")
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}
