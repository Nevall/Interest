package com.interest.myapplication.view.news;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.interest.myapplication.Entity.newsEntity.StoriesEntity;
import com.interest.myapplication.R;
import com.interest.myapplication.Utils.Constant;
import com.interest.myapplication.Utils.PreUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/3/30.
 */
public class NewsAdapter extends BaseAdapter {
    private List<StoriesEntity> datas;
    private LayoutInflater inflater;
    private Context context;

    /**
     * 插入原数据
     */
    public void addList(List<StoriesEntity> items) {
        this.datas.addAll(items);
        notifyDataSetChanged();
    }
    /**
     * 刷新Adapter
     */
    public void refresh(){
        datas.clear();
        notifyDataSetChanged();
    }
    public NewsAdapter(List<StoriesEntity> datas, Context context) {
        if (datas==null){
            datas = new ArrayList<>();
        }
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public StoriesEntity getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder holder = null;
        if (convertView==null){
            holder = new holder();
            convertView = inflater.inflate(R.layout.main_news_item,null);
            holder.ivTitle = (ImageView) convertView.findViewById(R.id.iv_title);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvTopic = (TextView) convertView.findViewById(R.id.tv_topic);
            convertView.setTag(holder);
        }else{
            holder = (holder) convertView.getTag();
        }
        StoriesEntity storiesEntity = datas.get(position);
        //判断是否已读,设置标题文字颜色
        String readSequence = PreUtil.getStringFromDefault(context, "read", "");
        if (readSequence.contains(datas.get(position).getId()+"")) {
            holder.tvTitle.setTextColor(Color.GRAY);
        } else {
            holder.tvTitle.setTextColor(Color.BLACK);
        }
        if (storiesEntity.getType() == Constant.TOPIC) {
            ((FrameLayout) holder.tvTopic.getParent()).setBackgroundColor(Color.TRANSPARENT);
            holder.tvTitle.setVisibility(View.GONE);
            holder.ivTitle.setVisibility(View.GONE);
            holder.tvTopic.setVisibility(View.VISIBLE);
            holder.tvTopic.setText(storiesEntity.getTitle());
        }else {
            ((FrameLayout) holder.tvTopic.getParent()).setBackgroundResource(R.drawable.item_background_selector_light);
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.ivTitle.setVisibility(View.VISIBLE);
            holder.tvTopic.setVisibility(View.GONE);
            holder.tvTitle.setText(storiesEntity.getTitle());
            Picasso.with(context).load(storiesEntity.getImages().get(0)).into(holder.ivTitle);
        }
        return convertView;
    }
    class holder{
        public TextView tvTopic;
        public TextView tvTitle;
        public ImageView ivTitle;
    }
}
