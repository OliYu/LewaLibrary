package com.lb.news.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kxyu on 16-8-12.
 */
public class NewsDetailDataEntry {


    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;


    @SerializedName("animate")
    public String animate;

    @SerializedName("result_object")
    public NewsDetail newsDetailList;
}
