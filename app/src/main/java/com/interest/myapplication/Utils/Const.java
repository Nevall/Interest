package com.interest.myapplication.Utils;

/**
 * Created by Android on 2016/3/22.
 */
public class Const {

    /**
     * 机器人交谈api
     */
    public static final String SERVER_URL = "http://route.showapi.com/913-1";
    /**
     * 易源网应用key
     */
    public static final String KEY = "17173";
    /**
     * 易源网应用密钥
     */
    public static final String secret = "516dd2084f5e4a1eb7d99a34ab734dd4";
    /**
     * Bmob appid
     */
    public static final String APPID = "e4029d90eb02e61fe4c4f589fcacb567";
    /**
     * 豆瓣网查询书本信息URL
     */
    public static final String BOOK_URL = "https://api.douban.com/v2/book/search";
    /**
     * 豆瓣网查询电影URL
     */
    public static final String MOVIE_URL= "https://api.douban.com/v2/movie/in_theaters";
    /**
     * 图书列表第一次刷新
     */
    public static final int MESSAGE_FIRST_UPDATE_BOOK = 0;
    /**
     * 图书列表再次刷新
     */
    public static final int MESSAGE_UPDATE_BOOK = 1;
    /**
     * 加载更多的图书
     */
    public static final int MESSAGE_LOAD_MORE = 2;
    /**
     * 机器人回话返回数据成功
     */
    public static final int MESSAGE_CHAT_OK = 3;
    /**
     * 未知的网络错误
     */
    public static final int MESSAGE_OTHER_ERROR = 4;
}
