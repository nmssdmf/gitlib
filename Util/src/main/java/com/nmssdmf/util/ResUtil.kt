package com.nmssdmf.util

import android.content.Context
import android.os.Build

/**
 * 资源获取相关工具
 */
class ResUtil {
    companion object{
        /**
         * 获取dimen 的px值
         */
        @JvmStatic
        fun getPxSize(context: Context, dimenId: Int): Int {
            return context.resources.getDimensionPixelSize(dimenId)
        }

        /**
         * 获取dimen 的 dp值
         */
        @JvmStatic
        fun getDpSize(context: Context, dimenId: Int): Float {
            return Densityutil.pxToDp(context, context.resources.getDimension(dimenId))
        }

        /**
         * 获取 color
         */
        @JvmStatic
        fun getColor(context: Context, colorId: Int): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return context.resources.getColor(colorId, null)
            } else {
                return context.resources.getColor(colorId)
            }
        }
    }
}