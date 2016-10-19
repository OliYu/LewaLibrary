package com.lb.news.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lb.news.newsrequest.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        NewsModel.getInstance(this).requestNewsList(new NewsModel.NewsRequestListener(){
//            @Override
//            public void onNewsRequestFailed(String message) {
//                super.onNewsRequestFailed(message);
//            }
//
//            @Override
//            public void onNewsRequestSuccess(List<News> newsList) {
//                super.onNewsRequestSuccess(newsList);
//            }
//        });
        NewsModel.getInstance(this).getToken(new NewsModel.NewsRequestListener()
        {
            @Override
            public void onTokenRequestSuccess(String token) {
                String mtoken = token;
                super.onTokenRequestSuccess(token);
            }

            @Override
            public void onTokenRequestFailed(String message) {
                super.onTokenRequestFailed(message);
            }
        });

    }
}
