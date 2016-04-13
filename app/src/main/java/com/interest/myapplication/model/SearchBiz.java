package com.interest.myapplication.model;


import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.interest.myapplication.Entity.bookEntity.BookListEntity;
import com.interest.myapplication.Utils.Const;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.util.Map;

/**
 * 搜索图书
 * Created by Android on 2016/3/24.
 */
public class SearchBiz  {
    private static SearchBiz instance;
    private Handler handler;
    private HttpUtils httpUtils;

    private static int currentStart = 1;
    private Map<String, String> requestParams;

    private SearchBiz(){
        super();
    }

    public static SearchBiz newInstance(){
        if (instance==null){
            instance = new SearchBiz();
        }
        return instance;
    }
    public boolean isFirst = true;
    /**
     * 重载方法，适用于刷新界面时加载数据
     * @param handler
     */
    public void searchBook(Handler handler){
        isFirst = false;
        currentStart+=16;   //每次同样参数的请求都会增加count，下次刷新从上一个请求的位置开始
        searchBook(requestParams,handler);
    }
    /**
     * 搜索豆瓣API的图书
     * @param requestParams 0:书名 1：显示页数 2：显示页
     */
    public void searchBook(Map<String,String> requestParams, final Handler handler) {
        if (isFirst){
            currentStart=0;
        }
        this.handler = handler;
        this.requestParams = requestParams;
        String bookName = requestParams.get("q");
        String tag = requestParams.get("tag");
        httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        if (bookName!=null) {
            params.addQueryStringParameter("q", bookName);
        }
        if(tag!=null) {
            params.addQueryStringParameter("tag", tag);
        }
        params.addQueryStringParameter("count","16");
        params.addQueryStringParameter("start",String.valueOf(currentStart));
        String url = Const.BOOK_URL;
        httpUtils.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                BookListEntity bookListEntity = JSON.parseObject(result, BookListEntity.class);
                Message msg = Message.obtain();
                msg.obj = bookListEntity.getBooks();
                if (isFirst) {
                    msg.what = Const.MESSAGE_FIRST_UPDATE_BOOK;
                }else if(!isFirst){
                    msg.what = Const.MESSAGE_UPDATE_BOOK;
                }
                handler.sendMessage(msg);
                isFirst = true;
            }

            @Override
            public void onFailure(HttpException e, String s) {
                handler.sendEmptyMessage(Const.MESSAGE_OTHER_ERROR);
                e.printStackTrace();
            }
        });
    }
}
