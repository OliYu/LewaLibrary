package com.lb.news.okhttp.builder;

import com.lb.news.okhttp.OkHttpUtils;
import com.lb.news.okhttp.request.OtherRequest;
import com.lb.news.okhttp.request.RequestCall;

/**
 * Created by mseven on 5/25/16.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
