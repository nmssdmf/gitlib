package com.nmssdmf.util

import android.app.Activity
import android.content.Intent
import android.net.Uri

class SystemIntentUtil {
    companion object {
        /**
         * 跳转到拨打系统电话页面
         */
        @JvmStatic
        fun goCallPhonePage(activity: Activity, phoneNum:String){
            var intent =
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNum"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
        }

        /**
         * 打开系统浏览器
         */
        @JvmStatic
        fun goSystemBrowerPage(activity: Activity, url:String){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory("android.intent.category.BROWSABLE")
            intent.component = null
            activity.startActivity(intent)
        }
    }

}