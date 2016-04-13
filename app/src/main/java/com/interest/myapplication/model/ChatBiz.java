package com.interest.myapplication.model;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.interest.myapplication.Entity.chatEntity.ChatEntity;
import com.interest.myapplication.Entity.chatEntity.ChatResult;
import com.interest.myapplication.MyApplication;
import com.interest.myapplication.Utils.Const;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Android on 2016/3/22.
 */
public class ChatBiz {
    private static ChatBiz instance;
    private final SimpleDateFormat format;

    private ChatBiz(){
        super();
        format = new SimpleDateFormat("yyyyMMddHHmmss");
    }
    public static ChatBiz newInstance(){
        if (instance==null){
            instance = new ChatBiz();
        }
        return instance;
    }

    /*
    type	Number		类型为“int”回复类型： 回复类型列表 0	无回复 1	标准回复 2	列表 3	其它 4
    聊天(通用聊天库) 5	敏感词 6	重复 7	对话预处理的直接回复 8	聊天(定制聊天库) 9	过期 10	拼写错误 11
    建议问 >100	指令
       content	String		回复内容 例如：“很高兴为您服务！”
     */
    public void send(String content, final Handler handler){
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("question",content);
        params.addQueryStringParameter("showapi_appid", Const.KEY);
        params.addQueryStringParameter("showapi_sign", Const.secret);
        params.addQueryStringParameter("showapi_timestamp", format.format(new Date()));
        params.addQueryStringParameter("userid", MyApplication.userName);
        String url = Const.SERVER_URL;
        httpUtils.send(HttpMethod.GET, url, params, new RequestCallBack<String>(){
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                ChatResult chatResult = JSON.parseObject(json, ChatResult.class);
                if (chatResult.getShowapi_res_code()==0){
                    Message msg = Message.obtain();
                    msg.obj = chatResult.getShowapi_res_body().getContent();
                    msg.what = Const.MESSAGE_CHAT_OK;
                    handler.sendMessage(msg);
                }else{
                    handler.sendEmptyMessage(Const.MESSAGE_OTHER_ERROR);
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                handler.sendEmptyMessage(Const.MESSAGE_OTHER_ERROR);
            }
        });
    }
}
