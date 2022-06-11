package com.taopao.music;

import android.os.Binder;
import android.util.Log;

/**
 * @Author: TaoPao
 * @Date: 3/16/21 8:36 PM
 * @Description: java类作用描述
 */
public class MusicServiceBinder extends Binder {
    public MusicPlayerController mController;
    public MusicServiceBinder(MusicPlayerController controller) {
        mController = controller;
    }
    public void play(String id) {
        Log.e("=====", "play: " + id);
    }
}