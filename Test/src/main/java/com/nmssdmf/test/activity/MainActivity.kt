package com.nmssdmf.test.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.nmssdmf.base.BaseActivity
import com.nmssdmf.test.R
import com.nmssdmf.test.databinding.ActivityMainBinding
import com.nmssdmf.util.LogUtil

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initBundleData(bundle: Bundle) {
    }

    override suspend fun initData() {
    }

    override fun initView() {
        binding.btnUtil.setOnClickListener {
            startActivity(Intent(this, UtilActivity::class.java))
        }

        binding.btnBaselib.setOnClickListener {
            startActivity(Intent(this, BaseLibActivity::class.java))
        }

        binding.btnPlayerDemo.setOnClickListener {
            startActivity(Intent(this, PlayDemoActivity::class.java))
        }
    }
}