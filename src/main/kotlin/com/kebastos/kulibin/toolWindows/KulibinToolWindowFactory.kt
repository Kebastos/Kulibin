package com.kebastos.kulibin.toolWindows

import com.intellij.execution.impl.ConsoleViewImpl
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

const val KULIBIN_TOOL_WINDOW_NAME = "KulibinTerminal"

class KulibinToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow,
    ) {
        val consoleView: ConsoleView = ConsoleViewImpl(project, true)

        consoleView.print("Welcome to Kulibin builds!\n", ConsoleViewContentType.NORMAL_OUTPUT)

        if (toolWindow.contentManager.contentCount == 0) {
            val contentFactory = ContentFactory.getInstance()
            val content = contentFactory.createContent(consoleView.component, project.name, false)
            toolWindow.contentManager.addContent(content)
        }

        toolWindow.show()
    }
}
