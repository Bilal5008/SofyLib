package com.example.proxy


open class BaseActivityStart : ApplicationStart() {
    override fun onCreate() {
        super.onCreate()
        println("This is BaseActivityCalling")
    }
}