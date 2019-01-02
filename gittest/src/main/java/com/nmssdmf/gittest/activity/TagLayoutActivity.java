package com.nmssdmf.gittest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nmssdmf.gittest.R;
import com.nmssdmf.gittest.databinding.ActivityTagLayoutBinding;

public class TagLayoutActivity extends AppCompatActivity {

    private ActivityTagLayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tag_layout);
        initView();
    }

    private void initView() {
        for (int i = 0; i< 400; i++) {
            TextView view = new TextView(this);
            view.setText("Tag " + i);
            view.setBackgroundResource(R.color.colorPrimary);
            binding.tlRule.addView(view);
        }

        String string = "abcdefghijklmnopqrstuvwxyz";
        for (int i =1; i< 25; i++) {
            TextView view = new TextView(this);
            view.setText(string.substring(0, i));
            view.setBackgroundResource(R.color.colorAccent);
            binding.tlIrregular.addView(view);
        }
    }
}
