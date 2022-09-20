package com.nmssdmf.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.nmssdmf.gitlib.view.BaseActivity;
import com.nmssdmf.gitlib.view.adapter.BaseAdapter;
import com.nmssdmf.gitlib.view.util.ResUtil;
import com.nmssdmf.gittest.activity.TagLayoutActivity;
import com.nmssdmf.gittest.adapter.MainListAdapter;
import com.nmssdmf.gittest.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private List<String> dataList = new ArrayList<>();
    private MainListAdapter adapter;

    @Override
    protected ActivityMainBinding getContentViewId() {
        return ActivityMainBinding.inflate(LayoutInflater.from(activity));
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {
        binding.tl.setTitle("列表页");
        binding.tl.setTitleTextColor(ResUtil.getColor(R.color.colorPrimary));
        binding.rv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        dataList.add(ResUtil.getString(R.string.TagLayout));

        adapter = new MainListAdapter(activity, dataList);
        adapter.setListener(onAdapterItemClickListener);
        binding.rv.setAdapter(adapter);
    }

    private BaseAdapter.OnAdapterItemClickListener<String> onAdapterItemClickListener = (view, obj, position) -> {
        if (ResUtil.getString(R.string.TagLayout).equals(obj)) {
            startActivity(new Intent(MainActivity.this, TagLayoutActivity.class));
        }
    };

}
