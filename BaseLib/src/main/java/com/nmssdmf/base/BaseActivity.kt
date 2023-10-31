package com.nmssdmf.base

import android.app.Activity
import android.graphics.Color
import android.net.http.UrlRequest.Status
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.nmssdmf.base.databinding.ActivityBaseBinding
import com.nmssdmf.base.view.TitleBar
import com.nmssdmf.util.LogUtil
import com.nmssdmf.util.ResUtil
import com.nmssdmf.util.StatusBarUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/***
 * activity基类
 * 初始化顺序为
 * initViewBinding 初始化viewbinding
 * initBundleData 获取传参
 * initData 获取数据
 * initView 初始化view
 */
open abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    lateinit var baseBinding: ActivityBaseBinding
    lateinit var binding: T
    lateinit var activity: AppCompatActivity
    lateinit var mainscope:CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        initBaseView()
        setContentView(baseBinding.root)
        hideSystemStatusBar()
        initCustomStatusBar()

        if (intent != null && intent.extras != null) {
            initBundleData(intent.extras!!)
        }

        mainscope = CoroutineScope(SupervisorJob() + Dispatchers.Main + CoroutineName("BaseActivityInit") + defaultExceptionHandler)

        mainscope.launch {
            initData()
            initView()
        }
    }

    private fun initBaseView(){
        initCustomStatusBar()

        baseBinding.titleBar.onNavigationClickLintener = View.OnClickListener {
            onBackPressed()
        }

        addContentView()
    }


    private fun addContentView(){
        binding = initViewBinding()
        var params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        params.addRule(RelativeLayout.BELOW, R.id.llTop)
        baseBinding.llRoot.addView(binding.root.rootView, params)
    }
    /**
     * 设置statusbar高度，建议xml中id=vStatus,如果不是，或者样式问题，则需重写该方法
     */
    private fun initCustomStatusBar(){
        var params = baseBinding.vStatus.layoutParams
        params.height = StatusBarUtil.getStatusBarHeight(activity)
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        baseBinding.vStatus.layoutParams = params
        baseBinding.vStatus.setBackgroundColor(ResUtil.getColor(activity, R.color.title_bar_bg_color))
    }

    fun hideStatusView(){
        baseBinding.vStatus.visibility = View.GONE
    }

    fun hideTitleBar(){
        baseBinding.titleBar.visibility = View.GONE
    }

    fun hideTop(){
        baseBinding.llTop.visibility = View.GONE
    }

    /**
     * 默认设置为沉浸式状态栏
     */
    private fun hideSystemStatusBar(){
        StatusBarUtil.setStatusBarTransparent(activity)
        StatusBarUtil.setStatusBarTextColorBlack(activity)
    }

    private val defaultExceptionHandler = object : CoroutineExceptionHandler {
        override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

        override fun handleException(context: CoroutineContext, exception: Throwable) {
            exception.printStackTrace()
            onInitCoroutineError()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mainscope.cancel()
    }

    /**
     * 获取viewBinding
     */
    abstract fun initViewBinding(): T

    /**
     * 初始化页面跳转的传参
     */
    abstract fun initBundleData(bundle: Bundle)

    /**
     * 初始化网络数据/其他异步数据，通过协程操作
     */
    abstract suspend fun initData()

    /**
     * 初始化View，在initdata执行结束后调用
     */
    abstract fun initView()

    /**
     * 当initData和initView 的协程出现异常时调用
     */
    fun onInitCoroutineError(){}
}