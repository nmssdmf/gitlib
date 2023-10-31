package com.nmssdmf.test.util.activity

import android.os.Bundle
import com.nmssdmf.base.BaseActivity
import com.nmssdmf.test.R
import com.nmssdmf.test.databinding.ActivityRegexUtilBinding

class RegexUtilActivity : BaseActivity<ActivityRegexUtilBinding>() {
    override fun initViewBinding(): ActivityRegexUtilBinding {
        return ActivityRegexUtilBinding.inflate(layoutInflater)
    }

    override fun initBundleData(bundle: Bundle) {
    }

    override suspend fun initData() {
    }

    override fun initView() {
    }

}