package com.nmssdmf.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * 状态栏工具类
 * 依赖 androidx.core:core-ktx
 */
class StatusBarUtil {
    companion object {
        /**
         * 获取状态栏高度
         *
         * @param context context
         * @return 状态栏高度
         */
        @JvmStatic
        fun getStatusBarHeight(context: Context): Int {
            // 获得状态栏高度
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        }

        /**
         * 隐藏状态栏
         * 状态栏会变成全黑，不建议使用，
         * 直接使用  setStatusBarTransparent
         */
        @JvmStatic
        fun hideStatusBar(activity: Activity) {
            showOrHideStatusBar(activity, false)
        }

        /**
         * 显示状态栏
         */
        @JvmStatic
        fun showStatusBar(activity: Activity) {
            showOrHideStatusBar(activity, true)
        }

        /**
         * 隐藏或者显示状态栏
         */
        @JvmStatic
        private fun showOrHideStatusBar(activity: Activity, isShow: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (isShow) {
                    activity.window.insetsController?.show(WindowInsets.Type.statusBars())
                } else {
                    activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
                }
            } else {
                ViewCompat.getWindowInsetsController(activity.window.decorView)?.let {
                    if (isShow) {
                        it?.show(WindowInsetsCompat.Type.statusBars())
                    } else {
                        it?.hide(WindowInsetsCompat.Type.statusBars())
                    }
                }
            }
        }

        /**
         * 设置状态栏文字黑色
         */
        @JvmStatic
        fun setStatusBarTextColorBlack(activity: Activity){
            setStatusBarTextColor(activity, false)
        }

        /**
         * 设置状态栏文字白色
         */
        @JvmStatic
        fun setStatusBarTextColorWhite(activity: Activity) {
            setStatusBarTextColor(activity, true)
        }

        /**
         * 修改状态栏文字颜色
         * 只能设置黑白
         */
        @JvmStatic
        private fun setStatusBarTextColor(activity: Activity, isLight: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val state = if (isLight) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                activity.window.insetsController?.setSystemBarsAppearance(
                    state,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                ViewCompat.getWindowInsetsController(activity.window.decorView)?.let {
                    it?.isAppearanceLightStatusBars = isLight
                }
            }
        }

        /***
         * 设置状态栏透明
         */
        @JvmStatic
        fun setStatusBarTransparent(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                //insertControllerCompat?.show(WindowInsetsCompat.Type.systemBars()) // 展示系统view：状态栏+导航栏+系统标题栏。在使用getInsetsController后，
                //需先在xml styles隐藏actionBar（系统标题栏），不要用代码（如getSupportActionBar()?.hide()等）设置该系统标题栏隐藏，且insetsController不能单独隐藏系统标题栏
                //insertControllerCompat?.hide(WindowInsetsCompat.Type.navigationBars()) //隐藏底部手势导航栏
                //insertControllerCompat?.hide(WindowInsetsCompat.Type.statusBars())  //隐藏状态栏
                WindowCompat.setDecorFitsSystemWindows(activity.window, false) // 页面布局是否在状态栏下方，false：侵入状态栏
                activity.window.statusBarColor = Color.TRANSPARENT //状态栏背景色，侵入时最
            } else {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                val option =
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                window.decorView.systemUiVisibility = option
                window.statusBarColor = Color.TRANSPARENT
            }
        }
    }
}