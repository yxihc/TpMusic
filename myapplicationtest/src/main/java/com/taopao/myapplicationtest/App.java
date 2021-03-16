package com.taopao.myapplicationtest;

import android.app.Application;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.music.lake.musiclib.MusicPlayerConfig;
import com.music.lake.musiclib.MusicPlayerManager;
import com.music.lake.musiclib.bean.BaseMusicInfo;
import com.music.lake.musiclib.listener.BindServiceCallBack;
import com.music.lake.musiclib.listener.MusicRequestCallBack;
import com.music.lake.musiclib.listener.MusicUrlRequest;
import com.music.lake.musiclib.notification.NotifyManager;
import com.music.lake.musiclib.service.MusicPlayerService;

/**
 * @Author: TaoPao
 * @Date: 3/16/21 10:42 AM
 * @Description: java类作用描述
 */
public class App extends Application {
    private MPBroadcastReceiver mpBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        initMusicPlayer();
    }


    private void initMusicPlayer() {
        MusicPlayerConfig config = new MusicPlayerConfig.Builder()
                .setUseCache(false)
                .setUseExoPlayer(true)
                .setUrlRequest(musicUrlRequest)
                .create();
        MusicPlayerManager.getInstance().init(this, config);
        MusicPlayerManager.getInstance().initialize(this, new BindServiceCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed() {

            }
        });



        mpBroadcastReceiver = new MPBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(MusicPlayerService.ACTION_SERVICE);
        intentFilter.addAction(MusicPlayerService.META_CHANGED);
        intentFilter.addAction(MusicPlayerService.PLAY_QUEUE_CHANGE);
        intentFilter.addAction(NotifyManager.ACTION_MUSIC_NOTIFY);
        intentFilter.addAction(NotifyManager.ACTION_CLOSE);
        intentFilter.addAction(NotifyManager.ACTION_LYRIC);
        //注册广播
        registerReceiver(mpBroadcastReceiver, intentFilter);
    }

    private MusicUrlRequest musicUrlRequest = new MusicUrlRequest() {
        @Override
        public void checkNonValid(BaseMusicInfo baseMusicInfo, MusicRequestCallBack call) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_cover);
            call.onMusicBitmap(bitmap);
            call.onActionDirect();
        }
    };

}