package com.nmssdmf.test.util.activity

import android.os.Bundle
import android.view.View
import com.nmssdmf.test.activity.BaseTestListActivity
import com.nmssdmf.util.SystemIntentUtil

class SystemUtilActivity : BaseTestListActivity() {
    override fun initBundleData(bundle: Bundle) {

    }

    override suspend fun initData() {
        map["goSystemBrowerPage"] = View.OnClickListener {
            SystemIntentUtil.goSystemBrowerPage(activity, "https://www.baidu.com")
        }
        map["goCallPhonePage"] = View.OnClickListener {
            SystemIntentUtil.goCallPhonePage(activity, "123")
        }
    }

}