//package com.lb.news.http.manager;
//
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import android.util.SparseArray;
//
//import com.lb.news.Utils.DeviceUtil;
//import com.lb.news.Utils.NetUtil;
//import com.lb.news.bean.NewsList;
//import com.lb.news.bean.NewsChannel;
//import com.lb.news.bean.Register;
//import com.lb.news.http.Api;
//import com.lb.news.http.HostType;
//import com.lb.news.http.NewsRequesBody;
//import com.lb.news.http.service.INewsService;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.ref.WeakReference;
//import java.nio.charset.Charset;
//import java.nio.charset.UnsupportedCharsetException;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Cache;
//import okhttp3.CacheControl;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okio.Buffer;
//import okio.BufferedSource;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.jackson.JacksonConverterFactory;
//import rx.Observable;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Fuction: Retrofit请求管理类<p>
// */
//public class RetrofitManager {
//    private static final String TAG = "RetrofitManager";
//    //设缓存有效期为两天
//    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
//    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
//    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
//    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
//    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
//    private static final String CACHE_CONTROL_NETWORK = "max-age=0";
//
//    private static volatile OkHttpClient sOkHttpClient;
//
//    private INewsService mINewsService;
//
//    // 管理不同HostType的单例
//    private static SparseArray<RetrofitManager> sInstanceManager = new SparseArray<>(
//            HostType.TYPE_COUNT);
//
//    // 云端响应头拦截器，用来配置缓存策略
//    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            if (!NetUtil.isConnected(mContext.get())) {
//                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//                Log.e(TAG,"no network");
//            }
//            Response originalResponse = chain.proceed(request);
//
//            if (NetUtil.isConnected(mContext.get())) {
//                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//                String cacheControl = request.cacheControl().toString();
//                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma").build();
//            } else {
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC)
//                        .removeHeader("Pragma").build();
//            }
//        }
//    };
//
//    private void logRequestBodyToString(final RequestBody request) {
//        String requestBody = null;
//        try {
//            final RequestBody copy = request;
//            final Buffer buffer = new Buffer();
//            if (copy != null)
//                copy.writeTo(buffer);
//            else
//                requestBody = "";
//            requestBody = buffer.readUtf8();
//        } catch (final IOException e) {
//
//        }
//        Log.d("LewaRequestLog:",requestBody);
//    }
//
//    // 打印返回的json数据拦截器
//    private Interceptor mLoggingInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            final Request request = chain.request();
//            logRequestBodyToString(request.body());  //记录request数据。
//            final Response response = chain.proceed(request);
//
//            final ResponseBody responseBody = response.body();
//            final long contentLength = responseBody.contentLength();
//
//            BufferedSource source = responseBody.source();
//            source.request(Long.MAX_VALUE); // Buffer the entire body.
//            Buffer buffer = source.buffer();
//            Charset charset = Charset.forName("UTF-8");
//            MediaType contentType = responseBody.contentType();
//            if (contentType != null) {
//                try {
//                    charset = contentType.charset(charset);
//                } catch (UnsupportedCharsetException e) {
//                    Log.e(TAG,"");
//                    Log.e(TAG,"Couldn't decode the response body; charset is likely malformed.");
//                    return response;
//                }
//            }
//
//            if (contentLength != 0) {
//                Log.v(TAG,"--------------------------------------------开始打印返回数据----------------------------------------------------");
//                Log.d(TAG,buffer.clone().readString(charset).toString());
//                Log.v(TAG,"--------------------------------------------结束打印返回数据----------------------------------------------------");
//            }
//
//            return response;
//        }
//    };
//
//    private RetrofitManager() {
//    }
//
//    /**
//     * 获取单例
//     *
//     * @param hostType host类型
//     * @return 实例
//     */
//    public static RetrofitManager getInstance(int hostType,Context context) {
//        RetrofitManager instance = sInstanceManager.get(hostType);
//        if (instance == null) {
//            instance = new RetrofitManager(hostType,context);
//            sInstanceManager.put(hostType, instance);
//            return instance;
//        } else {
//            return instance;
//        }
//    }
//
//    private WeakReference<Context> mContext;
//    private RetrofitManager(@HostType.HostTypeChecker int hostType,Context context) {
//        mContext = new WeakReference<Context>(context);
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.getHost(hostType))
//                .client(getOkHttpClient()).addConverterFactory(JacksonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
//
//        mINewsService = retrofit.create(INewsService.class);
//    }
//
//    // 配置OkHttpClient
//    private OkHttpClient getOkHttpClient() {
//        if (sOkHttpClient == null) {
//            synchronized (RetrofitManager.class) {
//                if (sOkHttpClient == null) {
//                    // OkHttpClient配置是一样的,静态创建一次即可
//                    // 指定缓存路径,缓存大小100Mb
//                    if(mContext.get() == null) {
//                        return null;
//                    }
//                    Cache cache = new Cache(new File(mContext.get().getCacheDir(), "HttpCache"),
//                            1024 * 1024 * 100);
//                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
//                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
//                            .addInterceptor(mRewriteCacheControlInterceptor)
//                            .retryOnConnectionFailure(true)
//                            .readTimeout(50,TimeUnit.SECONDS)
//                            .connectTimeout(30, TimeUnit.SECONDS).build();
//                }
//            }
//        }
//        return sOkHttpClient;
//    }
//
//    /**
//     * 根据网络状况获取缓存的策略
//     *
//     * @return
//     */
//    @NonNull
//    private String getCacheControl() {
//        return NetUtil.isConnected(mContext.get()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
//    }
//
//    private String getNetworkType(){
//        return DeviceUtil.getNetWorkType(mContext.get());
//    }
//
//    /***
//     * @param channel      新闻类别
//     * @param action        下拉刷新或者加载更多
//     * @return 被观察对象
//     */
//    public Observable<ResponseObject<NewsList>> getNewsListObservable(String channel, String action, String read_tag,
//                                                                      String language, String token) {
//        return mINewsService.getNewsList(getNetworkType(),DeviceUtil.getDeviceId(mContext.get()),getCacheControl(),
//                new NewsRequesBody.NewsListBody(channel,action,read_tag,token),language)
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io());
//    }
//
//    /**
//     * 获取新闻频道
//     * */
//    public Observable<ResponseObject<NewsChannel>> getNewsChannelObservable(String language) {
//        return mINewsService.getNewsChannel(getNetworkType(),DeviceUtil.getDeviceId(mContext.get()),
//                getCacheControl(),language)
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io());
//    }
//
//    /**
//     * 获取新闻详情
//     * */
//    public Observable<ResponseObject<NewsDetails>> getNewsDetailsObservable(String id, String type,String language,String token) {
//        return mINewsService.getNewsDetails(getNetworkType(),DeviceUtil.getDeviceId(mContext.get()),getCacheControl(),
//                new NewsRequesBody.NewsDetailsBody(id,type,token),language)
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io());
//    }
//
//    /**
//     * 注册请求
//     * */
//    public Observable<ResponseObject<Register>> getRegisterTokenObservable(String deviceId, String screenResolution,String language) {
//        return mINewsService.getRegisterToken(getNetworkType(),DeviceUtil.getDeviceId(mContext.get()),
//                getCacheControl(),
//                new NewsRequesBody.RegisterBody(deviceId,screenResolution), language)
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io());
//    }
//
//
//}
