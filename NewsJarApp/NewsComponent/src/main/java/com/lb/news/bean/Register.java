package com.lb.news.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by oli on 16-6-4.
 */
public class Register {
    @SerializedName("status")
    public int status;
    @SerializedName("name")
    public String name;
    @SerializedName("gender")
    public int gender;
    @SerializedName("registered")
    public int registered;
    @SerializedName("atype")
    public int atype;
    @SerializedName("source")
    public String source;
    @SerializedName("token")
    public String token;
    @SerializedName("avatar")
    public List<String> avatar;
    @SerializedName("id")
    public String id;

}
