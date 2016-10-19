package com.lb.news.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Fuction: 请求数据host的类型<p>
 */
public class HostType {

    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 1;


    @HostTypeChecker
    public static final int NEWS = 1;

    public static final int NEWS_STAGING=2;

    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({NEWS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }

}
