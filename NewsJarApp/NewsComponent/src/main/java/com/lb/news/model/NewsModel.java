package com.lb.news.model;

import android.content.Context;
import android.support.annotation.Nullable;

import com.lb.news.Utils.DeviceUtil;
import com.lb.news.bean.NewsDetail;
import com.lb.news.bean.NewsList;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by oli on 16-8-30.
 */
public class NewsModel {
    private static final String TAG = "NewsModel";
    private static WeakReference<Context> mContext;
    private static NewsModel mNewsModel = null;
    private static String mLanguage = null;
    private static String mImei = null;
    private String mToken_English = null;
    private String mToken_India = null;

    public static NewsModel getInstance(Context context,String imei){
        if(mContext == null || mContext.get() == null) {
            mContext = new WeakReference<Context>(context);
        }
        if(mNewsModel == null) {
            mNewsModel = new NewsModel();
        }
        if(imei == null || imei.equals("")) {
            mImei = imei;
        }
        mLanguage = DeviceUtil.getLanguage(context);
        return mNewsModel;
    }

    private NewsModel(){

    }

    /**NewsList Request Callback Listener*/
    public static abstract class NewsRequestListener {
        static final int REQUEST_NEWS = 1;
        static final int REQUEST_NEWS_DETAILS = 2;
        static final int REQUEST_TOKEN = 3;
        int tag = -1;
        void setTag(int tag){
            this.tag = tag;
        }
        int getTag(){
            return tag;
        }
        /**NewsList List Request Success*/
        public void onNewsRequestSuccess(List<NewsList> newsList){

        }
        /**NewsList List Request failed*/
        public void onNewsRequestFailed(String message){

        }
        /**Get token success*/
        public void onTokenRequestSuccess(String token){

        }
        /**Get token failed*/
        public void onTokenRequestFailed(String message){

        }
        /**NewsList Details Request Success*/
        public void onNewsDetailsRequestSuccess(NewsDetail newsDetails){

        }
        /**NewsList Details Request failed*/
        public void onNewsDetailsRequestFailed(String message){

        }
    }

    /**Get NewsList List*/
    public void requestNewsList(@Nullable NewsRequestListener listener){
        if(mContext.get() == null) {
            return;
        }
        listener.setTag(NewsRequestListener.REQUEST_NEWS);
        String token = getToken(listener);
        if(token != null && !token.equals("")) {
            NewsRequest.requestNewsList(mContext.get(),listener,getForYouCategory(),
                    mLanguage,token);
        }
    }

    /**Get NewsList Details*/
    public void requestNewsDetails(@Nullable NewsRequestListener listener,String newsId,String type){
        if(mContext.get() == null) {
            return;
        }
        listener.setTag(NewsRequestListener.REQUEST_NEWS_DETAILS);
        String token = getToken(listener);
        if(token != null && !token.equals("")) {
            NewsRequest.requestNewsDetails(mContext.get(),listener,newsId,type,
                    mLanguage,token);
        }

    }
    /**get Token,this method return english or india token automatic*/
    public String getToken(NewsRequestListener listener){
        if(mContext.get() == null) {
            return "";
        }
        String token = null;
        //get token from ram memory first.
        if(DeviceUtil.ENGLISH.equalsIgnoreCase(DeviceUtil.getLanguage(mContext.get()))) {
            token = mToken_English;
        }
        else if(DeviceUtil.INDIA.equalsIgnoreCase(DeviceUtil.getLanguage(mContext.get()))) {
            token = mToken_India;
        }
        if(token != null && !token.equals("")) {
            return token;
        }
        //if token is null in ram memory,then get from news app by data provider.
        token = NewsContentResolver.queryByKey(mContext.get().getContentResolver(),DeviceUtil.getTokenKey(mContext.get()));
        //if token is null in news app, then request register.
        if(token == null || token.equals("")) {
            if(listener.getTag() == -1) {
                listener.setTag(NewsRequestListener.REQUEST_TOKEN);
            }
            NewsRequest.requestRegister(mInnerRequestCallBack,listener,mContext.get(),
                    DeviceUtil.getDeviceId(mContext.get(),mImei),DeviceUtil.getScreenResolution(mContext.get()),
                    mLanguage,DeviceUtil.getTokenKey(mContext.get()));
        }
        return token;
    }

    /**request callback in jar*/
    class InnerRequestCallBack {
        void onTokenRegisterSuccess(NewsRequestListener listener,String key, String value) {
            if (mContext.get() == null) {
                return;
            }
            if(DeviceUtil.ENGLISH.equalsIgnoreCase(DeviceUtil.getLanguage(mContext.get()))) {
                mToken_English = value;
            }
            else if(DeviceUtil.INDIA.equalsIgnoreCase(DeviceUtil.getLanguage(mContext.get()))) {
                mToken_India = value;
            }
            NewsContentResolver.insertKeyValue(mContext.get().getContentResolver(), key, value);
            if(listener.getTag() == NewsRequestListener.REQUEST_NEWS) {
                NewsRequest.requestNewsList(mContext.get(),listener,getForYouCategory(),
                        mLanguage,getToken(listener));
            }
            else if(listener.getTag() == NewsRequestListener.REQUEST_TOKEN) {
                listener.onTokenRequestSuccess(value);
            }

        }

        void onTokenRegisterFailed(NewsRequestListener listener) {
            if(listener.getTag() == NewsRequestListener.REQUEST_NEWS) {
               listener.onNewsRequestFailed("Register Token failed");
            }
            else if(listener.getTag() == NewsRequestListener.REQUEST_TOKEN) {
                listener.onTokenRequestFailed("Register Token failed");
            }
        }
    }

    private InnerRequestCallBack  mInnerRequestCallBack = new InnerRequestCallBack();

    private String getForYouCategory(){
        if(mContext.get() == null) {
            return "";
        }
        return "rec";
    }
}
