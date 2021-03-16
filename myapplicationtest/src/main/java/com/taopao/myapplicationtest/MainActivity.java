package com.taopao.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.music.lake.musiclib.MusicPlayerManager;
import com.music.lake.musiclib.bean.BaseMusicInfo;
import com.music.lake.musiclib.listener.MusicPlayEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String url = "https://wfs.heletech.cn/htalk-web/UploadRoot/audios/01f12fc1c28d4e2890353ad849c1f368.mp3";
    private ImageView mImageView;
    private ObjectAnimator mRotation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.iv);
        initAnimation();
    }
    private void initAnimation() {
        mRotation = ObjectAnimator.ofFloat(mImageView, "rotation", 0F, 359F);
        mRotation.setDuration(20 * 1000);
        mRotation.setRepeatCount(-1);
        mRotation.setRepeatMode(ObjectAnimator.RESTART);
        mRotation.setInterpolator(new LinearInterpolator());
        mRotation.start();
    }

    public void play(View view) {
        Log.e("MainActivity", "play: ");
        ArrayList<BaseMusicInfo> baseMusicInfos = new ArrayList<>();
        BaseMusicInfo baseMusicInfo = new BaseMusicInfo();
        baseMusicInfo.setUri(url);
        baseMusicInfo.setOnline(true);
        baseMusicInfo.setId(1);
        baseMusicInfo.setTitle("第一个");
        baseMusicInfos.add(baseMusicInfo);

        BaseMusicInfo baseMusicInfo1 = new BaseMusicInfo();
        baseMusicInfo1.setUri("https://wfs.heletech.cn/htalk-web/UploadRoot/audios/38d604b8a3bb47fb80b00aa3ff55d444.mp3");
        baseMusicInfo1.setOnline(true);
        baseMusicInfo1.setId(2);
        baseMusicInfo1.setTitle("第二个");
        baseMusicInfos.add(baseMusicInfo1);

        BaseMusicInfo baseMusicInfo3 = new BaseMusicInfo();
        baseMusicInfo1.setUri("https://wfs.heletech.cn/htalk-web/UploadRoot/audios/38d604b8a3bb47fb80b00aa3ff55d444.mp3");
        baseMusicInfo1.setTitle("第二个");
        baseMusicInfos.add(baseMusicInfo3);
        MusicPlayerManager.getInstance().addMusicPlayerEventListener(new MusicPlayEventListener() {
            @Override
            public void onChangePlayMusic(BaseMusicInfo baseMusicInfo) {
                Log.e("====", "onChangePlayMusic: " + baseMusicInfo.getUri());
            }

            @Override
            public void onLoading(boolean isLoading) {
                Log.e("====", "onLoading: " + isLoading);
            }

            @Override
            public void onPlaybackProgress(long curPosition, long duration, int bufferPercent) {
//                Log.e("====", "curPosition: "+curPosition +"duration:"+duration);
            }

            @Override
            public void onAudioSessionId(int audioSessionId) {
                Log.e("====", "onAudioSessionId: ");
            }

            @Override
            public void onPlayCompletion() {
                Log.e("====", "onPlayCompletion: ");
            }

            @Override
            public void onPlayStart() {
                Log.e("====", "onPlayStart: ");
            }

            @Override
            public void onPlayerStateChanged(boolean isPlaying) {
                Log.e("====", "onPlayerStateChanged: ");
            }

            @Override
            public void onPlayStop() {
                Log.e("====", "onPlayStop: ");
            }

            @Override
            public void onPlayerError(Throwable error) {
                Log.e("====", "onPlayerError: ");
            }

            @Override
            public void onUpdatePlayList(List<BaseMusicInfo> playlist) {

            }
        });

        MusicPlayerManager.getInstance().playMusic(baseMusicInfos, 0);
    }

}