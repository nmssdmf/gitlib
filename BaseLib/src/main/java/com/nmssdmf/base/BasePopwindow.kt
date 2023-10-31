package com.nmssdmf.base

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow

/**
 * popupwindow基类
 */
open abstract class BasePopwindow(activity: Activity, width: Int, height: Int):PopupWindow(width,height) {
    protected var activity: Activity

    init {
        this.activity= activity
    }

    open fun init() {
        contentView = initUI()
        isOutsideTouchable = true
        isFocusable = true
        setOnDismissListener { setWindowBg(1f) }
    }

    protected open fun setWindowBg(alpha: Float) {
        //popupwindow消失时使背景不透明
        val lp = activity!!.window.attributes
        lp.alpha = alpha //0.0-1.0
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        activity!!.window.attributes = lp
    }

    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        setWindowBg(0.3f)
    }

    protected abstract fun initUI(): View
}