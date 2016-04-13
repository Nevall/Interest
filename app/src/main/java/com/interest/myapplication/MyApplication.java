package com.interest.myapplication;

import android.app.Activity;
import android.app.Application;

import com.interest.myapplication.Entity.bookEntity.BookDetailEntity;
import com.interest.myapplication.Entity.newsEntity.Content;
import com.interest.myapplication.Entity.newsEntity.StoriesEntity;
import com.interest.myapplication.Utils.CacheDbHelper;
import com.interest.myapplication.Utils.WebCacheDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Android on 2016/3/22.
 */
public class MyApplication extends Application{
    private static CacheDbHelper cacheOpenhelper;
    private static WebCacheDbHelper webCacheOpenhelper;
    /**
     * 收藏夹的数据源
     */
    private List<BookDetailEntity> dataBook;
    private List<StoriesEntity> dataNews;

    public List<BookDetailEntity> getBookSaveData(){
        if (dataBook==null){
            dataBook = new ArrayList<>();
        }
        return dataBook;
    }
    public List<StoriesEntity> getNewsSaveData(){
        if (dataNews==null){
            dataNews = new ArrayList<>();
        }
        return dataNews;
    }
    public boolean setBookSaveData(BookDetailEntity book){
        if (book!=null&&!dataBook.contains(book)){
            dataBook.add(book);
            return true;
            }
        return false;
        }
    public boolean setNewsSaveData(StoriesEntity news){
        if (news!=null&&!dataNews.contains(news)){
            dataNews.add(news);
            return true;
        }
        return false;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        dataNews = new ArrayList<>();
        dataBook = new ArrayList<>();
        cacheOpenhelper = new CacheDbHelper(this, "cache.db", null, 1);
        webCacheOpenhelper = new WebCacheDbHelper(this, "WebCache.db", null, 1);
    }

    /**
     * 用户名
     */
    public static String userName = "herbib";
    private static List<Activity> activityList;

    public static boolean addActivity(Activity activity){
        if(activityList==null){
            activityList = new ArrayList<>();
        }
        if(activity!=null){
            activityList.add(activity);
            return true;
        }
        return false;
    }
    public static int getActivityIndex(Activity activity){
        if(activityList!=null){
            int index = activityList.indexOf(activity);
            return index;
        }
        return 0;
    }
    public static Activity getActivity(int index){
        if (activityList!=null){
            Activity activity = activityList.get(index);
            return activity;
        }
        return null;
    }
    public static CacheDbHelper getCacheDBHelper(){
        return cacheOpenhelper;
    }

    public static WebCacheDbHelper getWebCacheDBHelper(){
        return webCacheOpenhelper;
    }
}
