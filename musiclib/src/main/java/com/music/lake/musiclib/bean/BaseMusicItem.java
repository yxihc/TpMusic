package com.music.lake.musiclib.bean;

/**
 * @Author: TaoPao
 * @Date: 3/16/21 5:18 PM
 * @Description: java类作用描述
 */
public class BaseMusicItem<T> {

    private String id;
    private String cover;
    private String url;
    private String title;
    private T customData;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getCustomData() {
        return customData;
    }

    public void setCustomData(T customData) {
        this.customData = customData;
    }


}