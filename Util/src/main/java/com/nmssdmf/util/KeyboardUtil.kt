package com.nmssdmf.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 软键盘控制
 */
class KeyboardUtil {
    companion object{
        /**
         * 动态显示软键盘
         *
         * @param context 上下文
         * @param edit    输入框
         */
        @JvmStatic
        fun showSoftInput(context: Context, edit: EditText) {
            val inputManager = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(edit, 0)
        }

        /**
         * 软键盘隐藏
         */

        @JvmStatic
        fun hideSoftInput(context: Context, edit: EditText) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edit.windowToken, 0)
        }

    }
}