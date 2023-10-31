package com.nmssdmf.util

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * 正则表达式规范
 */
class RegexUtil {
    companion object {
        /**
         * 验证邮箱格式
         * @param email
         * @return
         */
        @JvmStatic
        fun isEmail(email: String?): Boolean {
            if (TextUtils.isEmpty(email)) {
                return false
            }
            val regex =
                "^[A-Za-z0-9\u4e00-\u9fa5]+(\\.[A-Za-z0-9\u4e00-\u9fa5]+)*@[A-Za-z0-9\u4e00-\u9fa5]+(\\.[A-Za-z0-9\u4e00-\u9fa5]+)*(\\.[A-Za-z\u4e00-\u9fa5]{2,})$"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }
}