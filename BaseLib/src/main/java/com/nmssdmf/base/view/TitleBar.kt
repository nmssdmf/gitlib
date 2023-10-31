package com.nmssdmf.base.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.nmssdmf.base.R
import com.nmssdmf.base.databinding.ViewTitleBarBinding

/**
 * @author mahuafeng
 * @date 2022/3/7
 */
open class TitleBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    BaseTitleBar(context, attrs, defStyleAttr){
    lateinit var binding: ViewTitleBarBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)
    init {

    }

    override fun getDefaultNavigationIcon():Int{
        return R.drawable.ic_title_bar_back_white
    }


    override fun getDefaultTitleColor():Int{
        return Color.parseColor("#ffffff")
    }

    override fun initBinding() {
        binding = ViewTitleBarBinding.inflate(LayoutInflater.from(context), this)
    }

    override fun getTtbEt(): EditText {
        return binding.tbEt
    }

    override fun getTtbTvTitle(): TextView {
        return binding.tbTvTitle
    }

    override fun getTtbLlEdit(): LinearLayout {
        return binding.tbLlEdit
    }

    override fun getTtbLlMenu(): LinearLayout {
        return binding.tbLlMenu
    }

    override fun getTtbIvClose(): ImageView {
        return binding.tbIvClose
    }

    override fun getTtbIvNavigation(): ImageView {
        return binding.tbIvNavigation
    }

    override fun getTtbIvAction(): ImageView {
        return binding.tbIvAction
    }

    override fun getToolbarHeight(): Int {
        return context.resources.getDimensionPixelSize(R.dimen.title_bar_height)
    }

    override fun setDefaultBackColor() {
        setBackgroundResource(R.color.title_bar_bg_color)
    }

}