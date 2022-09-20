package com.nmssdmf.gittest.activity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.nmssdmf.gitlib.view.BaseActivity;
import com.nmssdmf.gittest.R;
import com.nmssdmf.gittest.databinding.ActivityTagLayoutBinding;

public class TagLayoutActivity extends BaseActivity<ActivityTagLayoutBinding> {
    @Override
    protected ActivityTagLayoutBinding getContentViewId() {
        return ActivityTagLayoutBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {
        for (int i = 0; i< 50; i++) {
            TextView view = new TextView(this);
            view.setText("Tag " + i);
            view.setTextSize(14);
            view.setGravity(Gravity.CENTER);
            view.setBackgroundResource(R.color.colorPrimary);
            binding.tlRule.addView(view);
        }

        String string = "abcdefghijklmnopqrstuvwxyz";
        for (int i =1; i< string.length(); i++) {
            TextView view = new TextView(this);
            view.setText(string.substring(0, i));
            view.setTextSize(14);
            view.setGravity(Gravity.CENTER);
            view.setBackgroundResource(R.color.colorAccent);
            binding.tlIrregular.addView(view);
        }
    }

    @Override
    protected void initData() {

    }
}
