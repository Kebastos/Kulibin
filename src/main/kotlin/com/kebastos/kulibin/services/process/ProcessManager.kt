package com.kebastos.kulibin.services.process

object ProcessManager {
    private var currentProcessService: ProcessService? = null

    fun setProcessService(processService: ProcessService) {
        currentProcessService = processService
    }

    fun getProcessService(): ProcessService? {
        return currentProcessService
    }

    fun stopProcess() {
        currentProcessService?.stopProcess()
        currentProcessService = null // Обнуляем процесс после остановки
    }

    fun isProcessRunning(): Boolean {
        return currentProcessService?.isRunning() == true
    }
}
