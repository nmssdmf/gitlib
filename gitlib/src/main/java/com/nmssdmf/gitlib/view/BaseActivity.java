package com.nmssdmf.gitlib.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

/**
 * @author mahuafeng
 * @date 2022/9/14
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    protected T binding;
    protected AppCompatActivity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        binding = getContentViewId();
        setContentView(binding.getRoot());

        initIntentData();
        initView();
        initData();
    }

    /**
     * 获取binding
     * @return
     */
    protected abstract T getContentViewId();

    /**
     * 优先获取从外部传入的数据
     */
    protected abstract void initIntentData();

    /**
     * 其实初始化View
     */
    protected abstract void initView();

    /**
     * 最后获取数据展示
     */
    protected abstract void initData();
}
