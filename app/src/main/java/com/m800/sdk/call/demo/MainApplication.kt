package com.m800.sdk.call.demo

import androidx.multidex.MultiDexApplication
import com.m800.sdk.core.CinnoxCore
import com.m800.sdk.call.demo.utils.CrashHandler
import com.m800.sdk.core.logger.M800Logger

class MainApplication: MultiDexApplication() {

    var isActivityInForeground = false

    override fun onCreate() {
        super.onCreate()
        CrashHandler.init()
        M800Logger.setLoggerLevel(1)
        CinnoxCore.initialize(this, SERVICE_ID)
    }
}