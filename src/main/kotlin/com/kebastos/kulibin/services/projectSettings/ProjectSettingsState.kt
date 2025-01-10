package com.kebastos.kulibin.services.projectSettings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@Service(Service.Level.PROJECT)
@State(
    name = "KulibinProjectSettingsState",
    storages = [Storage("KulibinProjectSettingsState.xml")],
)
class ProjectSettingsState : PersistentStateComponent<ProjectInfoModel> {
    private var settings = ProjectInfoModel()

    override fun getState(): ProjectInfoModel {
        return settings
    }

    override fun loadState(state: ProjectInfoModel) {
        XmlSerializerUtil.copyBean(state, settings)
    }
}
