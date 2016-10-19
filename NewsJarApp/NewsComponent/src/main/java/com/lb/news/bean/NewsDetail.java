package com.lb.news.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lewa on 16-8-12.
 */
public class NewsDetail {


    @SerializedName("related_images")
    public ArrayList<ImageInfo> related_images;
    @SerializedName("source_url")
    public String source_url;
    @SerializedName("like_count")
    public int like_count;
    @SerializedName("id")
    public String id;
    @SerializedName("top_images")
    public List<ImageInfo> top_images;
    @SerializedName("title")
    public String title;
    @SerializedName("site_url")
    public String site_url;
    @SerializedName("subscribed")
    public String subscribed;
    @SerializedName("content")
    public String content;
    @SerializedName("source")
    public String source;
    @SerializedName("type")

    public String type;
    @SerializedName("liked")
    public int liked;
    @SerializedName("unlike_count")
    public int unlike_count;

    @SerializedName("favour")
    public List<NewsList> favour;


    @Override
    public String toString() {
        return " title=" + title + " type "+ type +"\n"+" content : "+content+
                '}';
    }

}
