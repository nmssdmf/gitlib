package com.nmssdmf.test.util.activity

import android.os.Bundle
import com.nmssdmf.base.BaseActivity
import com.nmssdmf.base.manager.toast.ToastUtil
import com.nmssdmf.test.databinding.ActivityStatusBarUtilBinding
import com.nmssdmf.util.StatusBarUtil

class StatusBarUtilActivity : BaseActivity<ActivityStatusBarUtilBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun initViewBinding(): ActivityStatusBarUtilBinding {
        return ActivityStatusBarUtilBinding.inflate(layoutInflater)
    }

    override fun initBundleData(bundle: Bundle) {
    }

    override suspend fun initData() {
    }

    override fun initView() {
        binding.btnGetStatusBarHeight.setOnClickListener {
            ToastUtil.showToast("状态栏高度为" + StatusBarUtil.getStatusBarHeight(activity))
        }
        binding.btnHideStatusBar.setOnClickListener{
            StatusBarUtil.hideStatusBar(activity)
        }
        binding.btnShowStatusBar.setOnClickListener{
            StatusBarUtil.showStatusBar(activity)
        }
        binding.btnSetStatusBarTextColorBlack.setOnClickListener{
            StatusBarUtil.setStatusBarTextColorBlack(activity)
        }
        binding.btnSetStatusBarTextColorWhite.setOnClickListener{
            StatusBarUtil.setStatusBarTextColorWhite(activity)
        }
        binding.btnSetStatusBarTransparent.setOnClickListener{
            StatusBarUtil.setStatusBarTransparent(activity)
        }
    }

}