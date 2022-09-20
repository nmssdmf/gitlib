package com.nmssdmf.gitlib.view.util

import com.nmssdmf.gitlib.view.BaseApp

/**
 * @author mahuafeng
 * 资源获取工具
 * @date 2022/9/20
 */
object ResUtil {
    @JvmStatic
    fun getString(stringId:Int):String{
        return BaseApp.getInstance().getString(stringId)
    }
}