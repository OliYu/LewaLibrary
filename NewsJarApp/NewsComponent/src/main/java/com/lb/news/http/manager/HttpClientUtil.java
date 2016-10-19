package com.lb.news.http.manager;

import com.lb.news.okhttp.OkHttpUtils;
import com.lb.news.okhttp.callback.StringCallback;

/**
 * Created by shunli on 16-7-25.
 */
public class HttpClientUtil {
    private static final String CONTENT_TYPE = "application/json";
    public static final String NEWS_HOST = "http://newsapi.revanow.com/api/";
    private static final String LIST_URL = "102";
    public static final String DETAIL_URL = "articles";
    public static final String REGISTER = "register";

    private static String getXscreenListUrl() {
        return NEWS_HOST + LIST_URL;
    }

    public static void useOkHttpPost(String jsonString, StringCallback callback, String url,String language) {
        OkHttpUtils
                .postString()
                .url(NEWS_HOST + url)
                .content(jsonString)
                .addHeader("language",language)
                .build()
                .execute(callback);
    }

    public static void useOkHttpPost(String jsonString, StringCallback callback) {
        useOkHttpPost(jsonString, callback, LIST_URL,"");

    }

    public static void useOkHttpGet(String Url, StringCallback callback){
            OkHttpUtils.get().url(Url).build().execute(callback);

    }
}
