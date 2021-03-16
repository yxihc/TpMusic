package com.taopao.myapplicationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.music.lake.musiclib.notification.NotifyManager;
import com.music.lake.musiclib.service.MusicPlayerService;
import com.music.lake.musiclib.utils.LogUtil;


public class MPBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = "MusicPlayerBroadCaster";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d(TAG, "MPBroadcastReceiver =" + intent.getAction());
        if (MusicPlayerService.META_CHANGED.equals(intent.getAction())) {
            //通知更新当前播放歌曲
        } else if (MusicPlayerService.PLAY_QUEUE_CHANGE.equals(intent.getAction())) {
        } else if (NotifyManager.ACTION_MUSIC_NOTIFY.equals(intent.getAction())) {
            toPlayerActivity(context);
        } else if (NotifyManager.ACTION_LYRIC.equals(intent.getAction())) {
        } else if (NotifyManager.ACTION_CLOSE.equals(intent.getAction())) {
            close(context);
        }
    }

    private void toPlayerActivity(Context context) {
//        //通知栏点击
//        Intent intent1 = new Intent(context, PlayerActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent1);
        Log.e(TAG, "toPlayerActivity: ");
    }


    private void close(Context context) {
        //通知栏点击
    }
}