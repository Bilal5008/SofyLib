package com.example.model

class AppInfo {

    var pkgName: String? = null
    var appName: String? = null
    var appVersion: String? = null
    var minSDK: String? = null
    var maxSDK: String?= null
    var compileSDK: String? = null
    var appIconFile: String? = null
    var appFile: String? = null

    constructor(
        pkgName: String?,
        appName: String?,
        appVersion: String?,
        minSDK: String?,
        maxSDK: String?,
        compileSDK: String?,
        appIconFile: String?,
        appFile: String?
    ) {
        this.pkgName = pkgName
        this.appName = appName
        this.appVersion = appVersion
        this.minSDK = minSDK
        this.maxSDK = maxSDK
        this.compileSDK = compileSDK
        this.appIconFile = appIconFile
        this.appFile = appFile
    }
}