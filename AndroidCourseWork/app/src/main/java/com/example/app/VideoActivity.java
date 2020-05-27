package com.example.app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class VideoActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private boolean play;
    private SurfaceHolder savedHolder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        surfaceView = findViewById(R.id.video_surfaceview);
        mediaPlayer = new MediaPlayer();

        if (getIntent() != null) {
            try {
                mediaPlayer.setDataSource(getIntent().getStringExtra("feedurl"));
                surfaceView.getHolder().addCallback(new PlayCallback());
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    //TODO 3.1.跳转后自动循环播放
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();
                        play = true;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class PlayCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mediaPlayer.setDisplay(holder);
            // TODO Q2.解决切换后台黑屏问题
            if (holder == savedHolder) {
                mediaPlayer.start();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            savedHolder = holder;
        }
    }

    public void onSurfaceViewClick(View view) {
        //TODO 3.2.点击暂停/播放功能
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        //TODO Q1.后台暂停播放
        super.onPause();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.pause();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
}
