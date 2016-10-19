package com.lb.news.http;

/**
 * Fuction: 请求接口<p>
 */
public class Api {
    public static final String NEWS_HOST = "http://newsapi.revanow.com/api/";
    /**测试服务*/
    /**目前将所有的频道一次请求的条数都设置为20*/
    public static final int NEWS_LIMIT = 20;
    /**请求后台没有更新的数据，返回的code值*/
    public static final int HTTP_NOT_MODIFY = 304;
    /**资源没有找到*/
    public static final int CODE_SOURCE_NOT_FOUND = 404;
    /**POST失败*/
    public static final int CODE_POST_ERROR = -1;
    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        switch (hostType) {
            case HostType.NEWS:
                return Api.NEWS_HOST;
        }
        return "";
    }

}
