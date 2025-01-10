package com.kebastos.kulibin.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@Service(Service.Level.APP)
@State(
    name = "KulibinApplicationSettingsState",
    storages = [Storage("KulibinApplicationSettingsState.xml")],
)
class ApplicationSettingsState : PersistentStateComponent<ApplicationSettingsModel> {
    private var settings = ApplicationSettingsModel()

    override fun getState(): ApplicationSettingsModel {
        return settings
    }

    override fun loadState(state: ApplicationSettingsModel) {
        XmlSerializerUtil.copyBean(state, settings)
    }
}
