package com.nmssdmf.test.activity;

import static androidx.media3.common.Player.EVENT_PLAY_WHEN_READY_CHANGED;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerControlView;
import androidx.media3.ui.PlayerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nmssdmf.base.BaseActivity;
import com.nmssdmf.test.R;
import com.nmssdmf.test.databinding.ActivityPlayDemoBinding;
import com.nmssdmf.util.LogUtil;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class PlayDemoActivity extends BaseActivity<ActivityPlayDemoBinding> {
    ExoPlayer player;

    PlayerControlView playerControlView;

    private float volume = 0;

    @NonNull
    @Override
    public ActivityPlayDemoBinding initViewBinding() {
        return ActivityPlayDemoBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initBundleData(@NonNull Bundle bundle) {

    }

    @Nullable
    @Override
    public Object initData(@NonNull Continuation<? super Unit> $completion) {
        return null;
    }

    @Override
    @OptIn(markerClass = UnstableApi.class)
    public void initView() {


        player = new ExoPlayer.Builder(this).build();
        binding.pv.setPlayer(player);

        playerControlView = findViewById(R.id.exo_controller);
//        playerControlView.findViewById(R.id.exo_bottom_bar).setVisibility(View.GONE);
        playerControlView.findViewById(R.id.exo_basic_controls).setVisibility(View.GONE);
        playerControlView.findViewById(R.id.exo_extra_controls_scroll_view).setVisibility(View.GONE);
        playerControlView.findViewById(R.id.exo_rew_with_amount).setVisibility(View.GONE);
        playerControlView.findViewById(R.id.exo_ffwd_with_amount).setVisibility(View.GONE);
        playerControlView.findViewById(R.id.exo_prev).setVisibility(View.GONE);
        playerControlView.findViewById(R.id.exo_next).setVisibility(View.GONE);

        player.addListener(new Player.Listener() {
            @Override
            public void onEvents(Player player, Player.Events events) {
                Player.Listener.super.onEvents(player, events);
                switch (EVENT_PLAY_WHEN_READY_CHANGED){

                }
            }
        });

//        binding.pv.setUseController(false);
        binding.pv.setControllerAutoShow(false);
//        binding.pv.setControllerHideOnTouch(false);

//
        volume = player.getVolume();
        LogUtil.d("volume = " + volume);
        player.setVolume(0);
        binding.ivSoundOff.setOnClickListener(v->{
            player.setVolume(volume);
            binding.ivSoundOff.setVisibility(View.GONE);
            });

        binding.ivClose.setOnClickListener(v->{
            if (binding.pv.getParent() == binding.rl) {
                return ;
            }
            binding.rlBig.removeView(binding.pv);
            binding.rl.addView(binding.pv);
        });



        binding.pv.setOnClickListener(v->{
            if (binding.pv.getParent() == binding.rlBig) {
                return ;
            }
            binding.rl.removeAllViews();
            binding.rlBig.addView(binding.pv);
        });


        MediaItem mediaItem = MediaItem.fromUri("https://video2.qfc.cn/4f1430e4dad145a59401ba5b3dccf1b1/d0e157b369fa4bb395faba16d28d19ce-f304d3eeb240c78015df42d63990e8c1-sd.mp4");
        player.setMediaItem(mediaItem);

        player.prepare();
        player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

}