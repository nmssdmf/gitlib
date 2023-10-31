package com.nmssdmf.base

import android.app.Application
import android.util.Log
import com.nmssdmf.base.manager.activity.ActivityMng
import com.nmssdmf.util.LogUtil

/**
 * Application基类，
 * 必须继承
 */
open class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
        initBase()
    }
    private fun initBase(){
        ActivityMng.INST.init(app!!)
    }
    companion object{
        @JvmStatic
        private var app: BaseApp? = null

        @JvmStatic
        fun getApplication():Application{
            return app!!
        }
    }
}