package com.taopao.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;

import com.danikula.videocache.HttpProxyCacheServer;
import com.taopao.music.utils.LogUtils;

/**
 * @Author: TaoPao
 * @Date: 4/2/21 10:15 AM
 * @Description: java类作用描述
 */
public class MusicMediaPlayer extends BasePlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private static final String TAG = "MusicMediaPlayer";
    private MediaPlayer mMediaPlayer;
    //是否已经初始化
    private boolean mIsInitialized = false;
    //是否已经初始化
    private boolean mIsPrepared = false;
    private Context context;


    public MusicMediaPlayer(final Context context) {
        this.context = context;
        initMediaPlayer();
        mMediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    public void setDataSource(final String path) {
        mIsInitialized = setDataSourceImpl(path);
    }

    private boolean setDataSourceImpl(final String path) {
        if (path == null) return false;
        try {
            mIsPrepared = false;
            mMediaPlayer.reset();
            if (listener != null) {
                listener.onPlayerStateChanged(false);
            }
            boolean cacheSetting = MusicPlayerManager.getInstance().isHasCache();
            LogUtils.d(TAG, "缓存设置：" + cacheSetting);
            //本地歌曲无需缓存
            if (path.startsWith("content://") || path.startsWith("/storage")) {
                mMediaPlayer.setDataSource(context, Uri.parse(path));
            } else if (cacheSetting) {
                //缓存开启，读取缓存
                HttpProxyCacheServer proxy = MusicPlayerManager.getProxy();
                String proxyUrl = proxy.getProxyUrl(path);
                LogUtils.d(TAG, "设置缓存,缓存地址：proxyUrl=" + proxyUrl);
                mMediaPlayer.setDataSource(proxyUrl);
            } else {
                //不缓存
                mMediaPlayer.setDataSource(path);
            }
            LogUtils.d(TAG, "prepareAsync");
            mMediaPlayer.prepareAsync();
            if (listener != null) {
                listener.onLoading(true);
            }
        } catch (Exception todo) {
            LogUtils.e(TAG, "Exception:" + todo.getMessage());
            todo.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        super.start();
        LogUtils.d(TAG, "start");
        mMediaPlayer.start();
        if (listener != null) {
            listener.onPlayerStateChanged(isPlaying());
        }
    }

    @Override
    public void stop() {
        super.stop();
        LogUtils.d(TAG, "stop");
        try {
            mMediaPlayer.reset();
            mIsInitialized = false;
            mIsPrepared = false;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void release() {
        super.release();
        LogUtils.d(TAG, "release");
        mMediaPlayer.release();
    }

    @Override
    public void pause() {
        super.pause();
        LogUtils.d(TAG, "pause");
        mMediaPlayer.pause();
        if (listener != null) {
            listener.onPlayerStateChanged(isPlaying());
        }
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    /**
     * 只有在mediaPlayer prepared之后才能调用，不然会报-38错误
     */
    public long duration() {
        if (mIsPrepared) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * 只有在mediaPlayer prepared之后才能调用，不然会报-38错误
     */
    @Override
    public long position() {
        if (mIsPrepared) {
            try {
                return mMediaPlayer.getCurrentPosition();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public void seekTo(long ms) {
        super.seekTo(ms);
        mMediaPlayer.seekTo((int) ms);
    }

    @Override
    public void setVolume(final float vol) {
        super.setVolume(vol);
        LogUtils.e("Volume", "vol = " + vol);
        try {
            mMediaPlayer.setVolume(vol, vol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAudioSessionId() {
        return mMediaPlayer.getAudioSessionId();
    }


    @Override
    public boolean isInitialized() {
        return mIsInitialized;
    }

    @Override
    public boolean isPrepared() {
        return mIsPrepared;
    }


    //    、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        LogUtils.d(TAG, "onPrepared");
        mIsPrepared = true;
        if (playWhenReady) {
            mediaPlayer.start();
        }
        if (listener != null) {
            listener.onPrepared();
            listener.onLoading(false);
            listener.onPlayerStateChanged(playWhenReady);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        LogUtils.e(TAG, "onBufferingUpdate" + percent);
        if (listener != null) {
            listener.onBufferingUpdate(mediaPlayer, percent);
        }
    }

    @Override
    public boolean onError(final MediaPlayer mp, final int what, final int extra) {
        LogUtils.e(TAG, "Music Server Error what: " + what + " extra: " + extra);
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                mIsInitialized = false;
                //播放错误，需要重新释放mediaPlayer
                mMediaPlayer.release();
                mMediaPlayer = new MediaPlayer();
                //唤醒锁，
                mMediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
                return true;
            default:
                break;
        }
        if (listener != null) {
            listener.onLoading(false);
            listener.onError();
        }
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        LogUtils.e(TAG, "onCompletion");
        if (mediaPlayer == mMediaPlayer && listener != null) {
            listener.onCompletionNext();
        } else if (listener != null) {
            listener.onCompletionEnd();
        }
    }
}