package com.lb.news.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lewa on 16-8-17.
 */
public class RegisterDataEntry {
    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;

    @SerializedName("result_object")
    public Register register;
}
