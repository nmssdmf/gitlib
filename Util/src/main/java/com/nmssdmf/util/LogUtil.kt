package com.nmssdmf.util

import android.text.TextUtils
import android.util.Log
import org.json.JSONObject


class LogUtil {
    companion object {
        /**用于log的开关，可以根据build类型设置，工具类中默认打开，可根据实际需求处理*/
        @JvmStatic
        var openLog = true

        @JvmStatic
        private var className: String? = null//类名

        @JvmStatic
        private var methodName: String? = null//方法名

        @JvmStatic
        private var lineNumber: Int = 0//行数

        @JvmStatic
        private fun createLog(log: String): String {
            var buffer = StringBuffer()
            buffer.append("================")
            buffer.append(methodName);
            buffer.append("(").append(className).append(":").append(lineNumber)
                .append(")================:\n")
            buffer.append(log)
            return buffer.toString()
        }

        /**
         * 获取文件名、方法名、所在行数
         * @param sElements
         */
        @JvmStatic
        private fun getMethodNames(sElements: Array<StackTraceElement>) {
            className = sElements[2].fileName
            methodName = sElements[2].methodName
            lineNumber = sElements[2].lineNumber
        }

        @JvmStatic
        fun d(text: String) {
            log("d", text)
        }

        /***
         * 将实体类打印成json格式
         */
        @JvmStatic
        fun <T : Any> printObjToJson(obj: T) {
            var jsonString = GsonUtil.toJson(obj)
            printJson(jsonString)
        }

        /***
         * 将字符串打印成json格式
         */
        @JvmStatic
        fun printJson(json:String){
            var jsonObject = JSONObject(json)
            log("d", jsonObject.toString(4))
        }

        @JvmStatic
        fun i(text: String) {
            log("i", text)
        }

        @JvmStatic
        fun e(text: String) {
            log("e", text)
        }

        @JvmStatic
        private fun log(type: String, text: String) {
            if (openLog) {
                if (TextUtils.isEmpty(text)) {
                    return
                }
                getMethodNames(Throwable().stackTrace)
                when (type) {
                    "d" -> Log.d(className, createLog(text))
                    "i" -> Log.i(className, createLog(text))
                    "e" -> Log.e(className, createLog(text))
                    else -> Log.d(className, createLog(text))
                }
            }
        }
    }
}