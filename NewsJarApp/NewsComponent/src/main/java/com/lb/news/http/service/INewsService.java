//package com.lb.news.http.service;
//
//import com.lb.news.bean.NewsChannel;
//import com.lb.news.bean.NewsList;
//import com.lb.news.bean.Register;
//import com.lb.news.http.NewsRequesBody;
//
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.Header;
//import retrofit2.http.POST;
//import retrofit2.http.Query;
//import rx.Observable;
//
///**
// * Fuction: 请求数据服务<p>
// */
//public interface INewsService {
//    /***请求新闻列表*/
//    @POST("102")
//    Observable<ResponseObject<NewsList>> getNewsList(
//            @Header("ntype") String nType,
//            @Header("did") String did,
//            @Header("Cache-Control") String cacheControl,
//            @Body NewsRequesBody.NewsListBody body,
//            @Query("language") String language);
//
//    /**请求新闻tab*/
//    @GET("catagory")
//    Observable<ResponseObject<NewsChannel>> getNewsChannel(
//            @Header("ntype") String nType,
//            @Header("did") String did,
//            @Header("Cache-Control") String cacheControl,
//            @Query("language") String language);
//
//    /**注册请求*/
//    @POST("register")
//    Observable<ResponseObject<Register>> getRegisterToken(
//            @Header("ntype") String nType,
//            @Header("did") String did,
//            @Header("Cache-Control") String cacheControl,
//            @Body NewsRequesBody.RegisterBody body,
//            @Query("language") String language);
//
//    /**获取新闻详情*/
//    @POST("articles")
//    Observable<ResponseObject<NewsDetails>> getNewsDetails(
//            @Header("ntype") String nType,
//            @Header("did") String did,
//            @Header("Cache-Control") String cacheControl,
//            @Body NewsRequesBody.NewsDetailsBody body,
//            @Query("language") String language);
//
//
//    /***请求location信息*/
//    @GET("city")
//    Observable<ResponseObject<String>> getLocation(
//            @Header("ntype") String nType,
//            @Header("did") String did,
//            @Header("Cache-Control") String cacheControl,
//            @Query("language") String language);
//
//
//}
