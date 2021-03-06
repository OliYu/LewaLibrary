package com.lb.news.okhttp.builder;


import com.lb.news.okhttp.request.PostStringRequest;
import com.lb.news.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by mseven on 5/25/16.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
