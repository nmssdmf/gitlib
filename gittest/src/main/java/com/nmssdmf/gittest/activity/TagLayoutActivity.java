package com.nmssdmf.gittest.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.nmssdmf.gittest.R;
import com.nmssdmf.gittest.databinding.ActivityTagLayoutBinding;

public class TagLayoutActivity extends AppCompatActivity {

    private ActivityTagLayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
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
}
