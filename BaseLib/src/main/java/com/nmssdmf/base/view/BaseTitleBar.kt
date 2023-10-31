package com.nmssdmf.base.view

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import com.nmssdmf.base.R
import com.nmssdmf.util.Densityutil
import com.nmssdmf.util.ResUtil

/**
 * @author mahuafeng
 * @date 2022/3/7
 */
open abstract class BaseTitleBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    Toolbar(context, attrs, defStyleAttr) {
    var onClickLintener: TitleBarOnClickLintener? = null
    var onNavigationClickLintener: OnClickListener? = null
    var s = initBinding()
    var textWatcher: TextWatcher
    var menuColor: Int
    init {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        var mode = a.getInt(R.styleable.TitleBar_titleBarMode, 0)
        var title = a.getString(R.styleable.TitleBar_titleBarTitle)
        var titleColor =
            a.getColor(R.styleable.TitleBar_titleBarTitleColor, getDefaultTitleColor())
        menuColor =
            a.getColor(R.styleable.TitleBar_titleBarMenuTextColor, Color.parseColor("#ffffff"))
        var navigation = a.getResourceId(
            R.styleable.TitleBar_titleBarNavigation,
            getDefaultNavigationIcon()
        )
        var bgTransparent = a.getBoolean(R.styleable.TitleBar_titleBarBgTransparent, false)

        a.recycle()
        //判断是搜索框还是标题
        if (mode == 1) {
            getTtbLlEdit().visibility = VISIBLE
            getTtbTvTitle().visibility = GONE
        } else {
            getTtbLlEdit().visibility = GONE
            getTtbTvTitle().visibility = VISIBLE
        }

        getTtbTvTitle().setTextColor(titleColor);
        title?.let {
            getTtbTvTitle().text = it
        }
        //返回按钮
        getTtbIvNavigation().setImageResource(navigation)
        getTtbIvNavigation().setOnClickListener {
            getTtbEt().isCursorVisible = false
            onNavigationClickLintener?.onClick(getTtbIvNavigation())
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                onClickLintener?.beforeTextChanged(s, start, count, after)

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //通过输入框的字符串长度判断，close按钮显示
                if (s?.length != 0) {
                    getTtbIvClose().visibility = VISIBLE
                } else {
                    getTtbIvClose().visibility = GONE
                }

                onClickLintener?.onTextChanged(s, start, before, count)
            }

            override fun afterTextChanged(s: Editable?) {
                onClickLintener?.afterTextChanged(s)
            }

        }

        getTtbIvClose().setOnClickListener {
            getTtbEt().setText("")
        }

        getTtbIvAction().setOnClickListener {
            onClickLintener?.onActionClick()
        }
        getTtbEt().addTextChangedListener(textWatcher)
        getTtbEt().setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                v: TextView?,
                actionId: Int,
                event: KeyEvent?
            ): Boolean {
                setCursorVisible(false)
                onClickLintener?.onEditorAction(v, actionId, event)
                return false
            }

        })
        getTtbEt().setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (MotionEvent.ACTION_DOWN == event?.action) {
                    getTtbEt().isCursorVisible = true
                }
                return false
            }

        })
        if (background == null) {
            setBgTransparent(bgTransparent)
        }
    }

    fun setActionDrawableId(drawableId: Int) {
        getTtbIvAction().setImageResource(drawableId)

        getTtbIvAction().visibility = if (drawableId != 0) {
            VISIBLE
        } else {
            GONE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //高度固定
        setMeasuredDimension(
            measuredWidth,
            getToolbarHeight()
        )
    }

    fun setTitle(text: String) {
        getTtbTvTitle().text = text
    }

    fun setTitleColor(colorId: Int) {
        getTtbTvTitle().setTextColor(ResUtil.getColor(context, colorId))
    }

    fun showNavigation(show: Boolean) {
        getTtbIvNavigation().visibility = if (show) VISIBLE else GONE
    }

    fun setIvNavigationIcon(drawableId: Int) {
        getTtbIvNavigation().setImageResource(drawableId)
    }

    fun setBgTransparent(isTransparent: Boolean) {
        //判断toolbar是否透明,默认为主题色
        if (isTransparent) {
            background = null
        } else {
            setDefaultBackColor()
        }
    }

    fun setToolBarBg(colorId: Int) {
        setBackgroundResource(colorId)
    }

    /**
     * 用于加载网络图片时使用
     */
    fun getCloseIcon(): ImageView {
        return getTtbIvClose()
    }

    fun clearMenu() {
        getTtbLlMenu().removeAllViews();
    }

    /**
     * 添加图片菜单
     */
    fun addMenu(iconResource: Int) {
        var menu = ImageView(context)
        menu.setImageResource(iconResource)
        menu.scaleType = ImageView.ScaleType.FIT_XY
        menu.setPadding(
            ResUtil.getPxSize(context, R.dimen.qb_px_4),
            ResUtil.getPxSize(context, R.dimen.qb_px_4),
            ResUtil.getPxSize(context, R.dimen.qb_px_4),
            ResUtil.getPxSize(context, R.dimen.qb_px_4)
        )
        var menuParams =
            LinearLayout.LayoutParams(
                ResUtil.getPxSize(context, R.dimen.qb_px_32),
                ResUtil.getPxSize(context, R.dimen.qb_px_32)
            )
        menuParams.gravity = Gravity.CENTER_VERTICAL
        var position = getTtbLlMenu().childCount
        menu.setOnClickListener {
            onClickLintener?.onMenuClick(position)
        }
        getTtbLlMenu().addView(menu, menuParams)
    }

    /**
     * 添加文字菜单
     */
    fun addMenu(menuTitle: String) {
        var menu = TextView(context)
        menu.text = menuTitle
        menu.textSize =
            Densityutil.pxToDp(context, ResUtil.getPxSize(context, R.dimen.qb_px_15).toFloat())
        menu.setTextColor(menuColor)
        menu.gravity = Gravity.CENTER
        menu.setPadding(
            0,
            ResUtil.getPxSize(context, R.dimen.qb_px_6),
            ResUtil.getPxSize(context, R.dimen.qb_px_6),
            ResUtil.getPxSize(context, R.dimen.qb_px_6)
        )
        var menuParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        menuParams.gravity = Gravity.CENTER_VERTICAL
        var position = getTtbLlMenu().childCount
        menu.setOnClickListener {
            onClickLintener?.onMenuClick(position)
        }
        getTtbLlMenu().addView(menu, menuParams)
    }

    /**
     * 添加menu并返回
     */
    fun addMenuAndGetMenu(menuTitle: String): TextView {
        var menu = TextView(context)
        menu.text = menuTitle
        menu.textSize =
            Densityutil.pxToDp(context, ResUtil.getPxSize(context, R.dimen.qb_px_15).toFloat())
        menu.setTextColor(menuColor)
        menu.gravity = Gravity.CENTER
        menu.setPadding(
            0,
            ResUtil.getPxSize(context, R.dimen.qb_px_6),
            ResUtil.getPxSize(context, R.dimen.qb_px_6),
            ResUtil.getPxSize(context, R.dimen.qb_px_6)
        )
        var menuParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        menuParams.gravity = Gravity.CENTER_VERTICAL
        var position = getTtbLlMenu().childCount
        menu.setOnClickListener {
            onClickLintener?.onMenuClick(position)
        }
        getTtbLlMenu().addView(menu, menuParams)
        return menu
    }


    fun setTncToolBarOnClickLintener(l: TitleBarOnClickLintener?) {
        onClickLintener = l
    }

    /**
     * 获取搜索框文字
     */
    fun getEditString(): String {
        return getTtbEt().text.toString()
    }

    /**
     * 获取搜索框文字
     */
    fun setEditString(text: String) {
        getTtbEt().setText(text)
    }

    /**
     * 获取搜索框文字,是否触发TextWatcher
     */
    fun setEditString(text: String, isTextWatcher: Boolean) {
        if (!isTextWatcher) {
            getTtbEt().removeTextChangedListener(textWatcher)
        }
        if (text == null) {
            getTtbEt().setText("")
        } else {
            getTtbEt().setText(text)
        }
        getTtbEt().text?.let { getTtbEt().setSelection(it.length) }

        if (!isTextWatcher) {
            getTtbEt().addTextChangedListener(textWatcher)
        }
    }

    /**
     * 设置搜索框静态下显示文字
     */
    fun setHintString(hint: String) {
        getTtbEt().hint = hint
    }

    fun setCloseIcon(closeIconId: Int) {
        getTtbIvClose().setImageResource(closeIconId)
    }


    fun setMenuIcon(position: Int, menuIconId: Int) {
        if (getTtbLlMenu()[position] is ImageView) {
            (getTtbLlMenu()[position] as ImageView).setImageResource(menuIconId)
        }
    }

    fun setMenuText(position: Int, text: String) {
        if (getTtbLlMenu()[position] is TextView) {
            (getTtbLlMenu()[position] as TextView).text = text
        }
    }

    fun setMenuTextColor(color: Int) {
        for (i in 0 until getTtbLlMenu().childCount) {
            var view = getTtbLlMenu().getChildAt(i)
            if (view is TextView) {
                view.setTextColor(ResUtil.getColor(context, color))
            }
        }
        menuColor = ResUtil.getColor(context, color);
    }

    fun getMenuLayout(): LinearLayout {
        return getTtbLlMenu()
    }


    /**
     * 设置光标是否显示
     */
    fun setCursorVisible(show: Boolean) {
        getTtbEt().isCursorVisible = show
    }

    fun getSearchBar(): EditText {
        return getTtbEt()
    }

    fun getSearchBarParentLayout(): LinearLayout {
        return getTtbLlEdit()
    }

    override fun setNavigationOnClickListener(listener: OnClickListener?) {
        onNavigationClickLintener = listener
    }


    abstract fun getDefaultNavigationIcon(): Int
    abstract fun getDefaultTitleColor(): Int

    abstract fun initBinding()

    abstract fun getTtbEt():EditText
    abstract fun getTtbTvTitle():TextView
    abstract fun getTtbLlEdit():LinearLayout
    abstract fun getTtbLlMenu():LinearLayout
    abstract fun getTtbIvClose():ImageView
    abstract fun getTtbIvNavigation():ImageView
    abstract fun getTtbIvAction():ImageView

    abstract fun getToolbarHeight():Int

    abstract fun setDefaultBackColor()

    abstract class TitleBarOnClickLintener {
//        /**
//         * 导航按钮点击
//         */
//        abstract fun onNavigationClick()

        /**
         * 菜单按钮点击，多个菜单通过positon区分
         */
        open fun onMenuClick(position: Int){}

        /**
         * 搜索框没有动画时，触发点击按钮
         */
        open fun onSearchBarClick(){}

        /**
         * 触发close按钮的特殊功能
         */
        open fun onActionClick(){};

        /**
         * 键盘搜索监听
         */
        open fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?){}

        /**
         * 输入框文件变化
         */
        open fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        open fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        open fun afterTextChanged(s: Editable?) {}
    }
}