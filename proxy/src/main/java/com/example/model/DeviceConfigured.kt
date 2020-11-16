package com.example.model

class DeviceConfigured {
    var configured = false
    var deviceAPICode: String? = null
    var deviceName: String? = null
    var deviceResolution: String? = null
    var deviceRAM: String? = null
    var deviceType: String? = null
    var deviceManufacturer: String? = null
    var deviceModel: String? = null
    var deviceBoard: String? = null
    var deviceCPU: String? = null
    var deviceOSBuild: String? = null
    var deviceAPILevel: String? = null
    var deviceID: String? = null
    var platform: String? = null

    constructor(
        configured: Boolean,
        deviceAPICode: String?,
        deviceName: String?,
        deviceResolution: String?,
        deviceRAM: String?,
        deviceType: String?,
        deviceManufacturer: String?,
        deviceModel: String?,
        deviceBoard: String?,
        deviceCPU: String?,
        deviceOSBuild: String?,
        deviceAPILevel: String?,
        deviceID: String?,
        platform: String?
    ) {
        this.configured = configured
        this.deviceAPICode = deviceAPICode
        this.deviceName = deviceName
        this.deviceResolution = deviceResolution
        this.deviceRAM = deviceRAM
        this.deviceType = deviceType
        this.deviceManufacturer = deviceManufacturer
        this.deviceModel = deviceModel
        this.deviceBoard = deviceBoard
        this.deviceCPU = deviceCPU
        this.deviceOSBuild = deviceOSBuild
        this.deviceAPILevel = deviceAPILevel
        this.deviceID = deviceID
        this.platform = platform
    }
}