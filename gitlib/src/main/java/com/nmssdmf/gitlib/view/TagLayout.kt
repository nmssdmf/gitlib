package com.nmssdmf.gitlib.view

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import java.util.*

import com.nmssdmf.gitlib.R

/**
 * Created by ${nmssdmf} on 2018/8/6 0006.
 * 标签group
 */
class TagLayout : LinearLayout {
    companion object {
        const val TAG: String = "TagLayout"
    }

    /**
     * 描绘时候的坐标
     */
    private var l = 0
    private var r = 0
    private var t = 0
    private var b = 0

    /**
     * 限制显示的行数
     */
    private var lineCount = 0

    /**
     * childView之间的横向距离
     */
    private var horizontalSpace = 0

    /**
     * childView之间的纵向距离
     */
    private var verticalSpace = 0

    /**
     * 一行几个
     * 如果为0,表示为不规则排列
     * 如果大于0,则表示规则排列
     */
    private var horizontalCount = 0

    /**
     * view为key,坐标为value
     */
    private var map = Hashtable<View, RectF>()

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagLayout)
        horizontalCount = typedArray.getInteger(R.styleable.TagLayout_horizontal_count, 0)
        verticalSpace = typedArray.getDimension(R.styleable.TagLayout_vertical_space, 0f).toInt()
        horizontalSpace =
            typedArray.getDimension(R.styleable.TagLayout_horizontal_space, 0f).toInt()
        lineCount = typedArray.getInteger(R.styleable.TagLayout_line_count, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val superW = MeasureSpec.getSize(widthMeasureSpec) //layout的宽度
        val superWithMode = MeasureSpec.getMode(widthMeasureSpec) //layout的宽度类型
        val superH = MeasureSpec.getSize(heightMeasureSpec) //layout高度
        val superHeightMode = MeasureSpec.getMode(heightMeasureSpec) //layout的高度类型
        val childCount = childCount //含有view的数量
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        var maxRight = 0

        //初始化描绘的左上角坐标
        left = paddingLeft
        t = paddingTop
        var line_count = 1 //表示行数
        var horizontal_count = 0 //表示一行个数
        var isFirstOne = true //表示第一个view
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) {
                continue
            }
            /*
                (1) UPSPECIFIED :父容器对于子容器没有任何限制,子容器想要多大就多大.
                (2) EXACTLY父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间.
                (3) AT_MOST子容器可以是声明大小内的任意大小.
             */
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            val rectF = RectF()
            val childW: Int
            val childH = child.measuredHeight
            childW = if (horizontalCount > 0) { //规则布局
                if (superWithMode == MeasureSpec.AT_MOST) {
                    child.measuredWidth
                } else {
                    (superW - paddingLeft - paddingRight - (horizontalCount - 1) * horizontalSpace) / horizontalCount
                }
            } else { //不规则布局
                //测量childview的宽高
                child.measuredWidth
            }
            if (isFirstOne) {
                isFirstOne = false
                horizontal_count = 1
            } else {
                r = left + childW //如果不是第一个,则需要加上间距
                if (r + paddingRight > superW) { //超过容器宽度
                    if (lineCount > 0 && line_count + 1 > lineCount /* || top + childH + verticalSpace + childH + paddingBottom > superH*/) { //注释掉的代码为高度最大为superH
                        //超过限定行数或者大于group的高度
                        break
                    } else { //换行添加
                        line_count += 1
                        left = paddingLeft
                        t += childH + verticalSpace
                        horizontal_count = 1
                    }
                } else { //不换行添加
                    if (horizontalCount > 0 && horizontal_count == horizontalCount) { //超过规定个数,则换行
                        line_count += 1
                        left = paddingLeft
                        t += childH + verticalSpace
                        horizontal_count = 1
                    }
                    horizontal_count += 1
                }
            }
            b = t + childH
            r = left + childW
            if (r > maxRight) {
                maxRight = r
            }
            Log.d(TAG, "left = $left  top = $t  right= $r  bottom = $b")
            getRectF(rectF, left, t, r, b)
            map.put(child, rectF)
            left = r + horizontalSpace
        }
        val isHeightWrapContent =
            superHeightMode == MeasureSpec.AT_MOST || superHeightMode == MeasureSpec.UNSPECIFIED
        val isWidthtWrapContent =
            superHeightMode == MeasureSpec.AT_MOST || superHeightMode == MeasureSpec.UNSPECIFIED
        if (isHeightWrapContent && isWidthtWrapContent) { //当宽度/高度设置为wrap_content时候
            setMeasuredDimension(maxRight + paddingRight, b + paddingBottom)
        } else if (isHeightWrapContent) { //当高度设置为wrap_content时候
            setMeasuredDimension(superW, b + paddingBottom)
        } else if (isWidthtWrapContent) {
            setMeasuredDimension(maxRight + paddingRight, superH)
        } else {
            setMeasuredDimension(superW, superH)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            val rectF = map[child]
            if (rectF != null) {
                child.layout(
                    rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(),
                    rectF.bottom.toInt()
                )
            }
        }
    }

    private fun getRectF(rectF: RectF, left: Int, top: Int, right: Int, bottom: Int) {
        rectF.left = left.toFloat()
        rectF.top = top.toFloat()
        rectF.right = right.toFloat()
        rectF.bottom = bottom.toFloat()
    }
}