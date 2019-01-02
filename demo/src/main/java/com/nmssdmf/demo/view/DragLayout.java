package com.nmssdmf.demo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.nmssdmf.demo.R;

/**
 * Created by ${nmssdmf} on 2018/8/21 0021.
 */

public class DragLayout extends FrameLayout {
    private ViewDragHelper viewDragHelper;
    private View redView;

    public DragLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        redView = new View(getContext());
        redView.setBackgroundResource(R.color.colorAccent);
        addView(redView, 200, 200);
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == redView;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left < 0) {
                left = 0;
            } else if (left > getMeasuredWidth() - child.getMeasuredWidth()) {
                left = getMeasuredWidth() - child.getMeasuredWidth();
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top < 0) {
                top = 0;
            } else if (top > getMeasuredHeight() - child.getMeasuredHeight()) {
                top = getMeasuredHeight() - child.getMeasuredHeight();
            }
            return top;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        /**
         * 当手指抬起的时候,执行
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //当手指抬起的时候,在左半边,则往左靠边,在右半边则往右靠边
            int centerLeft = (getMeasuredWidth() - releasedChild.getMeasuredHeight()) / 2;
            if (releasedChild.getLeft() < centerLeft) {
                viewDragHelper.smoothSlideViewTo(releasedChild, 0, releasedChild.getTop());
                //刷新
                ViewCompat.postInvalidateOnAnimation(DragLayout.this);
            } else {
                viewDragHelper.smoothSlideViewTo(releasedChild, getMeasuredWidth() - releasedChild.getMeasuredWidth(), releasedChild.getTop());
                ViewCompat.postInvalidateOnAnimation(DragLayout.this);
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //计算view移动的百分比
            float fraction = changedView.getLeft() * 1f / (getMeasuredWidth() - changedView.getMeasuredWidth());
            executeAnim(fraction);
            if (left == 0 || left == getMeasuredWidth() - changedView.getMeasuredWidth()) {
                initData();
            }
        }
    };

    /**
     * 执行伴随动画
     *
     * @param fraction
     */
    private void executeAnim(float fraction) {
        //fraction: 0 - 1
        //缩放
//      ViewHelper.setScaleX(redView, 1+0.5f*fraction);
//      ViewHelper.setScaleY(redView, 1+0.5f*fraction);
        //旋转
//      ViewHelper.setRotation(redView,360*fraction);//围绕z轴转
//        ViewHelper.setRotationX(redView,360*fraction);//围绕x轴转
//      ViewHelper.setRotationY(redView,360*fraction);//围绕y轴转
//        ViewHelper.setRotationX(blueView,360*fraction);//围绕z轴转
        //平移
//      ViewHelper.setTranslationX(redView,80*fraction);
        //透明
//      ViewHelper.setAlpha(redView, 1-fraction);

    }

    private void initData() {
        // 首先创建一个SpringSystem对象
        SpringSystem springSystem = SpringSystem.create();
        // 添加一个弹簧到系统
        Spring spring = springSystem.createSpring();
        //设置弹簧属性参数，如果不设置将使用默认值
        //两个参数分别是弹力系数和阻力系数
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100, 1));
        // 添加弹簧监听器
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
                float value = (float) spring.getCurrentValue();
                //基于Y轴的弹簧阻尼动画
                redView.setTranslationY(value);

                // 对图片的伸缩动画
                float scale = 1f - (value * 0.5f);
                redView.setScaleX(scale);
                redView.setScaleY(scale);
            }
        });
        spring.setCurrentValue(1f);
        // 设置动画结束值
        spring.setEndValue(0f);

    }
}
