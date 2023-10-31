package com.nmssdmf.base.manager.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

/***
 * activity管理类
 * 用于获取顶部activity
 */
class ActivityMng: Application.ActivityLifecycleCallbacks{

    private var topActivity:WeakReference<Activity>? = null
    companion object {
        @JvmStatic
        val INST:ActivityMng = ActivityMng()
    }

    fun init(application: Application) {
        if (application == null) {
            throw Throwable("application is null")
        } else {
            application.unregisterActivityLifecycleCallbacks(INST)
            application.registerActivityLifecycleCallbacks(INST)
        }
    }

    fun getTopActivity(): Activity? {
        return topActivity?.get()
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        topActivity = WeakReference<Activity>(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        topActivity = WeakReference<Activity>(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        topActivity = WeakReference<Activity>(activity)
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }
}