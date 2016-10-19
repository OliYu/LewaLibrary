package com.lb.news.http;

import com.google.gson.Gson;

/**
 * Created by oli on 16-6-01.
 */
public class NewsRequesBody {

    /**注册Post参数*/
    public static class RegisterBody{
        public String source_id;
        public String phone_type="android";
        public String resolution;

        public RegisterBody(String source_id, String resolution) {
            this.source_id = source_id;
            this.resolution = resolution;
        }
    }

    /**请求新闻列表Post参数*/
    public static class NewsListBody {
        public String categories;
        public String action;
        public String token;
        public String read_tag;
        public NewsListBody(String channel,String action,String read_tag,String token) {
            this.categories = channel;
            this.action = action;
            this.token = token;
            this.read_tag = read_tag;
        }
        public String getParms() {
            return new Gson().toJson(this);
        }
    }
    /**请求新闻详情*/
    public static class NewsDetailsBody{
        public String id;
        public String type;
        public String token;
        public NewsDetailsBody(String id,String type,String token){
            this.id = id;
            this.type = type;
            this.token = token;
        }
    }

    /**请求相关新闻*/
    public static class RelatedNewsBody {
        public String id;
        public String token;
        public RelatedNewsBody(String id,String token) {
            this.token = token;
            this.id = id;
        }
    }

}
