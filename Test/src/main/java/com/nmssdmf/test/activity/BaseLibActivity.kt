package com.nmssdmf.test.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nmssdmf.base.listener.PreventMultipleClickManager
import com.nmssdmf.base.manager.toast.ToastUtil
import com.nmssdmf.test.baselib.activity.PreventMultipleClickActivity
import com.nmssdmf.util.KeyboardUtil

class BaseLibActivity : BaseTestListActivity() {
    override fun initBundleData(bundle: Bundle) {
    }

    override suspend fun initData() {
        map[ToastUtil::class.java.simpleName] = View.OnClickListener {
            ToastUtil.showToast("show")
        }

        map[PreventMultipleClickManager::class.java.simpleName] = View.OnClickListener {
            startActivity(Intent(this, PreventMultipleClickActivity::class.java))
        }
    }

}