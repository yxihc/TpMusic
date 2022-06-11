package com.taopao.music.listener;

import android.media.MediaPlayer;

/**
 * 播放回调
 */
public interface PlaybackListener {
    /**
     * 完成下一首
     */
    void onCompletionNext();

    /**
     * 完成结束
     */
    void onCompletionEnd();

    void onBufferingUpdate(MediaPlayer mp, int percent);

    void onPrepared();

    void onError();

    void onPlaybackProgress(long position, long duration, long buffering);

    void onLoading(boolean isLoading);

    void onPlayerStateChanged(boolean isPlaying);
}
