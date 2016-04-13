package com.interest.myapplication.Utils;

/**
 * Created by Android on 2016/3/5.
 * 全局常量
 */
public class Constant {
	//url路径
    public static final String BASEURL = "http://news-at.zhihu.com/api/4/";
    public static final String START = "start-image/1080*1776";//开始欢迎界面地址
    public static final String THEMES = "themes";//主题日报列表地址
    public static final String LATESTNEWS = "news/latest";//最新新闻消息地址
    public static final String BEFORE = "news/before/";//离线新闻消息地址
    public static final String THEMENEWS = "theme/";//主题日报内容地址
    public static final String THEME_BEFORE = "/before/";//过往主题日报内容地址
    public static final String CONTENT = "news/";//具体新闻内容地址
    public static final int TOPIC = 131;//具体新闻内容地址
    
    //MainFragment发送广播的ACTION
    public static final String ACTION_LOAD_FIRST_SUCCESS = "action_load_first_success";
    public static final String ACTION_LOAD_FIRST_FAILURE = "action_load_first_failure";
    public static final String ACTION_LOAD_FIRST_OFFLINE_SUCCESS = "action_load_first_offline_success";
    public static final String ACTION_LOAD_FIRST_OFFLINE_FAILURE = "action_load_first_offline_failure";
	public static final String ACTION_LOAD_BEFORE_SUCCESS = "action_load_before_success";
	public static final String ACTION_LOAD_BEFORE_FAILURE = "action_load_before_failure";
	public static final String ACTION_LOAD_BEFORE_OFFLINE_SUCCESS = "action_load_before_offline_success";
    public static final String ACTION_LOAD_BEFORE_OFFLINE_FAILURE = "action_load_before_offline_failure";
    
	//NewsActivity发送广播的ACTION
	public static final String ACTION_LOAD_NEWS_CONTENT_SUCCESS = "action_load_news_content_success";
	public static final String ACTION_LOAD_NEWS_CONTENT_FAILURE = "action_load_news_content_failure";
	public static final String ACTION_LOAD_NEWS_CONTENT_OFFLINE_SUCCESS = "action_load_news_content_offline_success";
	public static final String ACTION_LOAD_NEWS_CONTENT_OFFLINE_FAILURE = "action_load_news_content_offline_failure";
	
	//Cache.db数据库的表格名和字段名
	public static final String CACHE_TABLE_NAME = "CacheList";
	public static final String CACHE_COLUMN_ID = "id";
	public static final String CACHE_COLUMN_DATE = "date";
	public static final String CACHE_COLUMN_JSON = "json";
	
	//WebCache.db数据库的表格名和字段名
	public static final String WEB_CACHE_TABLE_NAME = "WebCache";
	public static final String WEB_CACHE_COLUMN_NEWSID = "newsId";
	public static final String WEB_CACHE_COLUMN_JSON = "json";
	
	public static final String START_LOCATION = "start_location";
    public static final String CACHE = "cache";
    public static final int BASE_COLUMN = 1010;
}
