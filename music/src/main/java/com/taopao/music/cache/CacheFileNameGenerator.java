package com.taopao.music.cache;

import com.danikula.videocache.file.Md5FileNameGenerator;
import com.taopao.music.utils.LogUtils;

/**
 * 处理相同歌曲不同的播放地址，生成同一个缓存文件名
 */
public class CacheFileNameGenerator extends Md5FileNameGenerator {
    @Override
    public String generate(String url) {
        int len = url.split("/").length;
        //分割"/"获得xxx.mp3... 字符串
        String newUrl = url.split("/")[len - 1].replace(".mp3", "");
        //分割"?"获得xxx.mp3... 字符串
        String newUrl1 = newUrl.split("\\?")[0];
        LogUtils.d("MusicPlayerEngine", "cache oldUrl =" + url);
        LogUtils.d("MusicPlayerEngine", "cache newUrl =" + newUrl);
        LogUtils.d("MusicPlayerEngine", "cache newUrl1 =" + newUrl1);
        return super.generate(newUrl1);
    }
}
