package com.nmssdmf.base.listener

import android.text.TextUtils
import android.view.View
import com.nmssdmf.base.manager.toast.ToastUtil

/**
 * 防多次点击事件
 * 针对单个按钮，按钮之间不影响
 * ps：使用时，view必须带id
 */
abstract class PreventMultipleClickListener (time: Int = 1000, toastStr:String = ""): View.OnClickListener{
    private var clickDelayTime = 0
    private var toastStr:String = ""

    private var preventMultipleClickManager:PreventMultipleClickManager = PreventMultipleClickManager.getInstance()

    init {
        this.toastStr = toastStr
        clickDelayTime = if (time <= 0) {
            1000
        } else {
            time
        }
    }

    override fun onClick(v: View?) {
        if (v == null || v.id == null || v.id == 0) {
            throw Exception("view is null or view.id is null")
        }
        if (preventMultipleClickManager.hasItem(v!!.id)) {
            if (!TextUtils.isEmpty(toastStr)) {
                ToastUtil.showToast(toastStr)
            }
            return
        }
        preventMultipleClickManager.addItem(v.id, System.currentTimeMillis())
        preventMultipleClickManager.removeItem(v.id, clickDelayTime.toLong())
        onMultiClick(v)
    }

    abstract fun onMultiClick(v: View?)

}