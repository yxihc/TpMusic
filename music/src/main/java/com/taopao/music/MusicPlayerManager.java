package com.taopao.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.danikula.videocache.HttpProxyCacheServer;
import com.taopao.music.cache.CacheFileNameGenerator;
import com.taopao.music.listener.BindServiceCallBack;

import java.io.File;
import java.util.WeakHashMap;

/**
 * @Author: TaoPao
 * @Date: 3/16/21 8:27 PM
 * @Description: java类作用描述
 */
public class MusicPlayerManager {
    //   ----------------------------- 单例 -----------------------------
    private MusicPlayerManager() {
    }

    private static class SingletonInstance {
        private static final MusicPlayerManager INSTANCE = new MusicPlayerManager();
    }

    public static MusicPlayerManager getInstance() {
        return MusicPlayerManager.SingletonInstance.INSTANCE;
    }

    private static final WeakHashMap<Context, ServiceBinder> mConnectionMap;

    static {
        mConnectionMap = new WeakHashMap<Context, ServiceBinder>();
    }

    private Context mContext;
    private ServiceToken mToken;

    private MusicServiceBinder mBinder;

    public void init(Context context) {
        this.mContext = context;
    }

    public void init(Context context, BindServiceCallBack callBack) {
        this.mContext = context;
    }

    public ServiceToken bind(Context context, BindServiceCallBack callBack) {
        mToken = bindToService(context, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mBinder = (MusicServiceBinder) iBinder;
                if (callBack != null) {
                    callBack.onSuccess();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mBinder = null;
                if (callBack != null) {
                    callBack.onFailed();
                }
            }
        });
        return mToken;
    }


    private final ServiceToken bindToService(final Context context,
                                             final ServiceConnection callback) {
//        Activity realActivity = ((Activity) context).getParent();
//        if (realActivity == null) {
//            realActivity = (Activity) context;
//        }
        try {
            //TODO 修复Android 8.0启动service异常报错 Not allowed to start service Intent { cmp=com.cyl.musiclake/.player.MusicPlayerService }: app is in background uid UidRecord{f44b6ce u0a208 TPSL idle procs:1 seq(0,0,0)}
            final ContextWrapper contextWrapper = new ContextWrapper(context);
            contextWrapper.startService(new Intent(contextWrapper, MusicPlayerService.class));
            final ServiceBinder binder = new ServiceBinder(callback, contextWrapper.getApplicationContext());
            if (contextWrapper.bindService(new Intent().setClass(contextWrapper, MusicPlayerService.class), binder, 0)) {
                mConnectionMap.put(contextWrapper, binder);
                return new ServiceToken(contextWrapper);
            }
        } catch (Exception e) {

        }
        return null;
    }


    private void unbindFromService(final ServiceToken token) {
        if (token == null) {
            return;
        }
        final ContextWrapper mContextWrapper = token.mWrappedContext;
        final ServiceBinder binder = mConnectionMap.get(mContextWrapper);
        if (binder == null) {
            return;
        }
        mContextWrapper.unbindService(binder);
        if (mConnectionMap.isEmpty()) {
            mBinder = null;
        }
    }


    public final class ServiceBinder implements ServiceConnection {
        private final ServiceConnection mCallback;
        private final Context mContext;

        public ServiceBinder(final ServiceConnection callback, final Context context) {
            mCallback = callback;
            mContext = context;
        }

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            mBinder = (MusicServiceBinder) service;
            if (mCallback != null) {
                mCallback.onServiceConnected(className, service);
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName className) {
            if (mCallback != null) {
                mCallback.onServiceDisconnected(className);
            }
            mBinder = null;
        }
    }

    public static final class ServiceToken {
        public ContextWrapper mWrappedContext;

        public ServiceToken(final ContextWrapper context) {
            mWrappedContext = context;
        }
    }


    public void play(String id) {
        mBinder.play(id);
    }


//    、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
    /**
     * AndroidVideoCache缓存设置
     */
    private HttpProxyCacheServer proxy;
    private String musicFilelCacheDir = null;
    private boolean mHasCache;

    public boolean isHasCache() {
        return mHasCache;
    }

    public void setHasCache(boolean mHasCache) {
        this.mHasCache = mHasCache;
    }

    public static HttpProxyCacheServer getProxy() {
        return MusicPlayerManager.getInstance().proxy == null ? (MusicPlayerManager.getInstance().proxy = MusicPlayerManager.getInstance().newProxy()) : MusicPlayerManager.getInstance().proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(mContext)
                .cacheDirectory(new File(musicFilelCacheDir))
                .fileNameGenerator(new CacheFileNameGenerator())
                .build();
    }
}


