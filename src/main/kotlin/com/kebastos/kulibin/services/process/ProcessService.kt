package com.kebastos.kulibin.services.process

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.impl.ConsoleViewImpl
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessListener
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.kebastos.kulibin.models.ScriptInfoModel
import com.kebastos.kulibin.toolWindows.KULIBIN_TOOL_WINDOW_NAME
import java.io.File
import kotlin.concurrent.thread

class ProcessService(private val project: Project, private val scriptInfo: ScriptInfoModel) {
    private val toolWindowManager = ToolWindowManager.getInstance(project)
    private val toolWindow: ToolWindow? = toolWindowManager.getToolWindow(KULIBIN_TOOL_WINDOW_NAME)

    private val scriptFile =
        if (scriptInfo.isCorrectInfo()) {
            File(scriptInfo.path)
        } else {
            throw NullPointerException("Temporary exception")
        }

    private val consoleView =
        toolWindow?.contentManager?.contents?.firstOrNull()?.component as? ConsoleViewImpl
            ?: throw IllegalStateException("ConsoleView not found in the ToolWindow")

    private var processHandler: ProcessHandler? = null // Ссылка на процесс

    fun execProcess() {
        if (!scriptFile.exists() || !scriptFile.isFile) {
            consoleView.print("Selected script file not found: ${scriptFile.path}", ConsoleViewContentType.ERROR_OUTPUT)
            return
        }

        val commandLine =
            GeneralCommandLine("cmd.exe", "/c", scriptFile.name, scriptFile.path).apply {
                setWorkDirectory(scriptFile.parentFile)
            }

        thread(start = true) {
            try {
                val handler =
                    OSProcessHandler(commandLine).apply {
                        addProcessListener(
                            object : ProcessListener {
                                override fun startNotified(event: ProcessEvent) {}

                                override fun processTerminated(event: ProcessEvent) {
                                    if (event.exitCode == 0) {
                                        consoleView.print("Process finished.\n", ConsoleViewContentType.NORMAL_OUTPUT)
                                    } else {
                                        consoleView.print("Process terminated.\n", ConsoleViewContentType.ERROR_OUTPUT)
                                    }
                                }

                                override fun processWillTerminate(
                                    event: ProcessEvent,
                                    willBeDestroyed: Boolean,
                                ) {
                                }

                                override fun onTextAvailable(
                                    event: ProcessEvent,
                                    outputType: Key<*>,
                                ) {
                                    consoleView.print(event.text, ConsoleViewContentType.NORMAL_OUTPUT)

                                    if (event.text.contains(" . . .", ignoreCase = true)) {
                                        pressKey()
                                    }
                                }
                            },
                        )

                        startNotify()
                    }

                synchronized(this) {
                    processHandler = handler
                }

                handler.waitFor()
            } catch (e: ExecutionException) {
                consoleView.print("Error executing script: ${e.message}\n", ConsoleViewContentType.ERROR_OUTPUT)
            }
        }
    }

    fun stopProcess() {
        processHandler?.let {
            it.destroyProcess()
            consoleView.print("Process stopped.\n", ConsoleViewContentType.ERROR_OUTPUT)
        } ?: run {
            consoleView.print("No process running to stop.\n", ConsoleViewContentType.ERROR_OUTPUT)
        }
    }

    fun isRunning(): Boolean {
        return processHandler?.isProcessTerminated == false
    }

    private fun pressKey() {
        try {
            val processInput = processHandler?.processInput

            if (processInput != null) {
                processInput.write("\n".toByteArray())
                processInput.flush()
            } else {
                consoleView.print("Process input is not available.\n", ConsoleViewContentType.ERROR_OUTPUT)
            }
        } catch (e: Exception) {
            consoleView.print("Error sending key press: ${e.message}\n", ConsoleViewContentType.ERROR_OUTPUT)
        }
    }
}
