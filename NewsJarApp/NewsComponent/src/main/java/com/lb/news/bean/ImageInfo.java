package com.lb.news.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oli on 16-10-12.
 */
public class ImageInfo{
    @SerializedName("origin")
    public String origin;
    @SerializedName("thumb_height")
    public int thumb_height;
    @SerializedName("thumb")
    public String thumb;
    @SerializedName("thumb_width")
    public int thumb_width;
    @SerializedName("width")
    public int width;
    @SerializedName("height")
    public int height;
    @SerializedName("source")
    public String source;
    @SerializedName("desc")
    public String desc;

    public ImageInfo(){

    }
}
