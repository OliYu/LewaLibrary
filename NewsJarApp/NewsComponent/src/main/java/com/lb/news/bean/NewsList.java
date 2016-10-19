package com.lb.news.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsList {
    /**公共的属性*/
    @SerializedName("top_images")
    public List<ImageInfo> top_images;
    @SerializedName("seq_id")
    public int seq_id;
    @SerializedName("title")
    public String title;
    @SerializedName("comments_count")
    public int comments_count;
    @SerializedName("has_commented")
    public boolean has_commented;
    @SerializedName("type")
    public String type;
    @SerializedName("id")
    public String id;
    @SerializedName("detail_url")
    public String detail_url;

    /**article*/
    @SerializedName("related_images")
    public List<ImageInfo> related_images;
    @SerializedName("is_hot")
    public int is_hot;
    @SerializedName("site_url")
    public String site_url;
    @SerializedName("source")
    public String source;
    @SerializedName("published_at")
    public String published_at;
    @SerializedName("op_recommend")
    public boolean op_recommend;
    @SerializedName("source_url")
    public String source_url;

    /**youtube_video and video*/
    @SerializedName("liked")
    public boolean liked;
    @SerializedName("favored")
    public boolean favored;
    @SerializedName("share_count")
    public int share_count;
    @SerializedName("youtube")
    public List<String> youtube;
    @SerializedName("share_url")
    public String share_url;
    @SerializedName("content")
    public String content;
    @SerializedName("like_count")
    public int like_count;
    @SerializedName("video")  //gif url
    public String video;

    /**galary*/

    public boolean isReaded;
}
