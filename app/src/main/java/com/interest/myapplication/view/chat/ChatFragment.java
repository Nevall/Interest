package com.interest.myapplication.view.chat;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.interest.myapplication.R;
import com.interest.myapplication.Utils.Const;
import com.interest.myapplication.Utils.HttpUtils;
import com.interest.myapplication.model.ChatBiz;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Android on 2016/3/29.
 */
public class ChatFragment extends Fragment {
    @ViewInject(R.id.tilChat)
    private TextInputLayout til;
    @ViewInject(R.id.btnSubmit)
    private Button btSubmit;
    @ViewInject(R.id.tvYouChat)
    private TextView tvYouChat;
    @ViewInject(R.id.tvMyChat)
    private TextView tvMyChat;
    @ViewInject(R.id.ivChat)
    private ImageView ivRobot;
    private LayoutInflater inflater;
    private AnimationDrawable drawable;
    private Animation animation;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Const.MESSAGE_CHAT_OK:
                    String content = (String) msg.obj;
                    tvYouChat.setText(content);
                    tvYouChat.startAnimation(animation);
                    drawable.start();
                    drawable.stop();
                    break;
                case Const.MESSAGE_OTHER_ERROR:
                    Toast.makeText(getActivity(), "网络有未知错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.activity_chat,null);
        ViewUtils.inject(this,view);
        drawable = (AnimationDrawable) getResources().getDrawable(R.drawable.chat_anim);
        ivRobot.setBackgroundDrawable(drawable);
        animation = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_chat);
        tvYouChat.setAnimation(animation);
        tvMyChat.setAnimation(animation);
        til.setHint("请输入内容");
        til.setHintAnimationEnabled(true);
        return view;
    }
    @OnClick(R.id.btnSubmit)
    public void submit(View v){
        if(HttpUtils.isNetworkConnected(getActivity())) {
            String content = til.getEditText().getText().toString();
            if (!"".equals(content)) {
                tvMyChat.setText(content);
                tvMyChat.startAnimation(animation);
                if (tvMyChat.getVisibility() == View.GONE){
                    tvMyChat.setVisibility(View.VISIBLE);
                }
                ChatBiz.newInstance().send(content, handler);
            }
            til.getEditText().setText("");
        }else{
            Toast.makeText(getActivity(), "没网了", Toast.LENGTH_SHORT).show();
        }
    }
}
