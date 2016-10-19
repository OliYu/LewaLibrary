package com.lb.news.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lewa on 16-8-17.
 */
public class NewsListDataEntry {
    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;

    @SerializedName("result_array")
    public ArrayList<NewsList> newsDataEntryList;
}
