package com.taopao.music;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @Author: TaoPao
 * @Date: 3/16/21 8:31 PM
 * @Description:
 */
public class MusicPlayerService extends Service implements MusicPlayerController {
    MusicServiceBinder mBinder = new MusicServiceBinder(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mBinder == null) mBinder = new MusicServiceBinder(this);
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        Log.e(TAG, "onCreate: ", );

    }
}