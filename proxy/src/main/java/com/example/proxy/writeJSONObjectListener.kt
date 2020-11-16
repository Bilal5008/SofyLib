package com.example.proxy

import com.an.deviceinfo.device.model.Device
import com.example.model.AppInfo
import com.example.model.DeviceConfigured
import com.example.model.SelectedComponent

interface writeJSONObjectListener {

    fun onSuccess(
            stringValue: String?,
            selectedComponent: SelectedComponent,
            actionValue: String?,
            device: DeviceConfigured,
            appInfo: AppInfo
    )

    fun onFailure(stringFailed: String?)
}