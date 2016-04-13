package com.interest.myapplication.model;

import android.os.Handler;
import android.os.Message;

import com.interest.myapplication.Utils.Const;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * Created by Android on 2016/3/30.
 */
public class MovieBiz {
    private MovieBiz instance;

    public MovieBiz newInstance(){
        if (instance==null){
            instance = new MovieBiz();
        }
        return instance;
    }

    public void searchMovie(final Handler handler){
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        String url = Const.MOVIE_URL;
        httpUtils.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Message msg = Message.obtain();
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }
}
