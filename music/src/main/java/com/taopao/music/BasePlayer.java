package com.taopao.music;

import com.taopao.music.listener.PlaybackListener;

/**
 * @Author: TaoPao
 * @Date: 4/2/21 10:04 AM
 * @Description: java类作用描述
 */
public class BasePlayer {
    /**
     * 准备好了直接播放
     */
    public boolean playWhenReady = true;
    PlaybackListener listener;

    public void stop() {
    }

    public void release() {
    }

    public void start() {
    }

    public void pause() {
    }

    public boolean isPlaying() {
        return false;
    }

    public boolean isPrepared() {
        return false;
    }

    public long position() {
        return 0;
    }

    public long duration() {
        return 0;
    }

    public boolean isInitialized() {
        return true;
    }

    public void seekTo(long ms) {
    }

    public int bufferedPercentage() {
        return 0;
    }

    public int getAudioSessionId() {
        return 0;
    }

    public void setDataSource(String uri) {
    }

    public void setVolume(float mCurrentVolume) {
    }

    public void setPlayBackListener(PlaybackListener listener) {
        this.listener = listener;
    }

}