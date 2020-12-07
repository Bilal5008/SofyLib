package com.example.myproxywithactivity

import android.app.Activity
import android.os.Bundle
import com.example.proxy.BaseActivityStart


class ApplicationClass : BaseActivityStart(){
    override fun onCreate() {
        super.onCreate()
        println("This is ApplicationClass")
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        super.onActivityCreated(p0, p1)
        println("This is onActivityCreated")
    }

    override fun onActivityStarted(p0: Activity) {
        super.onActivityStarted(p0)
    }

    override fun onActivityResumed(p0: Activity) {
        super.onActivityResumed(p0)
    }

    override fun onActivityPaused(p0: Activity) {
        super.onActivityPaused(p0)
    }

    override fun onActivityStopped(p0: Activity) {
        super.onActivityStopped(p0)
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        super.onActivitySaveInstanceState(p0, p1)
    }

    override fun onActivityDestroyed(p0: Activity) {
        super.onActivityDestroyed(p0)
    }
}