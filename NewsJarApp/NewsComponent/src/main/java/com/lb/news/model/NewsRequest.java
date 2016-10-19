package com.lb.news.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lb.news.bean.NewsDetailDataEntry;
import com.lb.news.bean.NewsListDataEntry;
import com.lb.news.bean.RegisterDataEntry;
import com.lb.news.http.NewsRequesBody;
import com.lb.news.http.manager.HttpClientUtil;
import com.lb.news.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by oli on 16-8-31.
 */
public class NewsRequest {
    private static final String TAG = "NewsJar";
    static void requestNewsList(Context context,final NewsModel.NewsRequestListener listener,String channel, String language,String token) {
        HttpClientUtil.useOkHttpPost(new NewsRequesBody.NewsListBody(channel, "next", "", token).getParms(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                listener.onNewsRequestFailed(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d(TAG, "response = " + response.toString());
                Gson gson = new Gson();
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                NewsListDataEntry dataEntry = new NewsListDataEntry();
                try {
                    dataEntry = gson.fromJson(response.trim(), NewsListDataEntry.class);
                }catch (Exception e){}
                    listener.onNewsRequestSuccess(dataEntry.newsDataEntryList);
            }
        });
    }

    static void requestNewsDetails(Context context, final NewsModel.NewsRequestListener listener,
                              String newsId, String type,String language,String token) {
        HashMap<String, String> parm = new HashMap<>();
        parm.put("type", type);
        parm.put("id", newsId);
        parm.put("token", token);
        Gson gson = new Gson();
        HttpClientUtil.useOkHttpPost(gson.toJson(parm), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                listener.onNewsDetailsRequestFailed(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();
                NewsDetailDataEntry newDetailDataEntry = null;
                try {
                    newDetailDataEntry = gson.fromJson(response.trim(), NewsDetailDataEntry.class);
                } catch (Exception e) {
                }

                if (newDetailDataEntry != null && newDetailDataEntry.newsDetailList != null) {
                        listener.onNewsDetailsRequestSuccess(newDetailDataEntry.newsDetailList);
                }

            }
        }, HttpClientUtil.DETAIL_URL,language);
    }

    static void requestRegister(final NewsModel.InnerRequestCallBack callBack, final NewsModel.NewsRequestListener listener,
                                        Context context, String deviceId, String screenResolution, String language,
                                        final String key) {
        HashMap<String, String> parm = new HashMap<>();
        parm.put("source_id", deviceId);
        parm.put("phone_type", "android");
        parm.put("resolution", screenResolution);
        Gson gson = new Gson();
        HttpClientUtil.useOkHttpPost(gson.toJson(parm), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                callBack.onTokenRegisterFailed(listener);
            }

            @Override
            public void onResponse(String response, int id) {
                if (TextUtils.isEmpty(response)) return;
                Log.d(TAG,"register success!!!");
                Gson gson = new Gson();
                RegisterDataEntry registerDataEntry = null;
                try {
                    registerDataEntry = gson.fromJson(response.trim(), RegisterDataEntry.class);
                } catch (Exception e) {
                }

                if (registerDataEntry != null && registerDataEntry.register != null) {
                    callBack.onTokenRegisterSuccess(listener,key,registerDataEntry.register.token);
                }

            }
        }, HttpClientUtil.REGISTER,language);
    }

//    static void requestRegister(final NewsModel.InnerRequestCallBack callBack, final NewsModel.NewsRequestListener listener,
//                                Context context, String deviceId, String screenResolution, String language,
//                                final String key) {
//        return RetrofitManager.getInstance(HostType.NEWS, context)
//                .getRegisterTokenObservable(deviceId, screenResolution, language)
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//
//                    }
//                }).subscribeOn(AndroidSchedulers.mainThread()) // 订阅之前操作在主线程
//                .doOnError(new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.e(TAG, "错误时处理：" + throwable + " --- " + throwable.getLocalizedMessage());
//                        callBack.onTokenRegisterFailed(listener);
//                    }
//                })
//                .subscribe(new Subscriber<ResponseObject<Register>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        try {
//                            Log.e(TAG, e.getLocalizedMessage() + "\n" + e);
//                            callBack.onTokenRegisterFailed(listener);
//                        } catch (Exception e1) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onNext(ResponseObject<Register> responseObject) {
//                        Log.d(TAG, "register success!!!");
//                        callBack.onTokenRegisterSuccess(listener, key, responseObject.result_object.token);
//                    }
//                });
//    }
}
