package com.taopao.tpmusicdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.taopao.music.MusicMediaPlayer;
import com.taopao.music.MusicPlayerManager;
import com.taopao.music.listener.BindServiceCallBack;
import com.taopao.music.listener.PlaybackListener;
import com.taopao.tpmusicdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        MusicPlayerManager.getInstance().init(this);

        initView();
    }

    private void initView() {
        mBinding.btnStart.setOnClickListener(v -> {
            MusicPlayerManager.getInstance().bind(this, new BindServiceCallBack() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed() {

                }
            });
        });
        mBinding.btnPlay.setOnClickListener(v -> {
//            MusicPlayerManager.getInstance().play("播放音频");

            MusicMediaPlayer musicMediaPlayer = new MusicMediaPlayer(this);
            musicMediaPlayer.setDataSource("https://wfs.heletech.cn/htalk-web/UploadRoot/audios/1565937825453511.mp3");
            musicMediaPlayer.setPlayBackListener(new PlaybackListener() {
                @Override
                public void onCompletionNext() {

                }

                @Override
                public void onCompletionEnd() {

                }

                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {

                }

                @Override
                public void onPrepared() {

                }

                @Override
                public void onError() {

                }

                @Override
                public void onPlaybackProgress(long position, long duration, long buffering) {

                }

                @Override
                public void onLoading(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean isPlaying) {
                    Toast.makeText(MainActivity.this, "sdasd", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}