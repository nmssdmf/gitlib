package com.nmssdmf.util

import android.content.Context

import android.util.TypedValue


/**
 * dp/px 转换
 */
class Densityutil {
    companion object {
        /**
         * dp转px
         *
         * @param context
         * @param val
         * @return
         */
        @JvmStatic
        fun dpToPx(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.resources.displayMetrics
            ).toInt()
        }

        /**
         * px转dp
         *
         * @param context
         * @param pxVal
         * @return
         */
        @JvmStatic
        fun pxToDp(context: Context, pxVal: Float): Float {
            val scale = context.resources.displayMetrics.density
            return pxVal / scale
        }
    }
}