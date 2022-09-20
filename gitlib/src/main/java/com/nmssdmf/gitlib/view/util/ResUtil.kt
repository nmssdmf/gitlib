package com.nmssdmf.gitlib.view.util

import android.os.Build
import com.nmssdmf.gitlib.view.BaseApp

/**
 * @author mahuafeng
 * 资源获取工具
 * @date 2022/9/20
 */
object ResUtil {
    /**
     * 字符串资源获取
     */
    @JvmStatic
    fun getString(stringId:Int):String{
        return BaseApp.getInstance().getString(stringId)
    }

    @JvmStatic
    fun getColor(colorId:Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BaseApp.getInstance().getColor(colorId)
        } else {
            BaseApp.getInstance().resources.getColor(colorId)
        }
    }
}