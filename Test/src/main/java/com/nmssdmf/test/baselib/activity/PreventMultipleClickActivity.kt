package com.nmssdmf.test.baselib.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nmssdmf.base.BaseActivity
import com.nmssdmf.base.listener.PreventMultipleClickListener
import com.nmssdmf.test.R
import com.nmssdmf.test.activity.BaseLibActivity
import com.nmssdmf.test.databinding.ActivityPreventMultipleClickBinding
import com.nmssdmf.util.LogUtil

class PreventMultipleClickActivity : BaseActivity<ActivityPreventMultipleClickBinding>() {
    override fun initViewBinding(): ActivityPreventMultipleClickBinding {
        return ActivityPreventMultipleClickBinding.inflate(layoutInflater)
    }

    override fun initBundleData(bundle: Bundle) {
    }

    override suspend fun initData() {
    }

    override fun initView() {
        binding.btn1.setOnClickListener(object :PreventMultipleClickListener(){
            override fun onMultiClick(v: View?) {
                LogUtil.d("btn1 click")
            }
        })

        binding.btn2.setOnClickListener(object :PreventMultipleClickListener(5000){
            override fun onMultiClick(v: View?) {
                LogUtil.d("btn2 click")
            }
        })

        binding.btn3.setOnClickListener(object :PreventMultipleClickListener(5000, "5秒钟还没到呢"){
            override fun onMultiClick(v: View?) {
                LogUtil.d("btn3 click")
            }
        })
    }

}