package com.nmssdmf.util

import com.google.gson.Gson

/**
 * Gson工具类，依赖gson库
 * 依赖 com.google.code.gson:gson
 */
class GsonUtil {
    companion object {
        @JvmStatic
        private var GSON = Gson()

        @JvmStatic
        fun toJson(obj: Any):String{
            return try {
                GSON.toJson(obj)
            } catch (e: Exception) {
                ""
            }
        }

        fun <T> fromJson(jsonString:String, cls:Class<T>):T?{
            return try {
                GSON.fromJson(jsonString, cls)
            } catch (e: Exception) {
                null
            }
        }
    }
}