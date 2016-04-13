package com.interest.myapplication.view.save;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.interest.myapplication.Entity.newsEntity.Content;
import com.interest.myapplication.Entity.newsEntity.StoriesEntity;
import com.interest.myapplication.MyApplication;
import com.interest.myapplication.R;
import com.interest.myapplication.view.news.NewsActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Android on 2016/4/6.
 */
public class SaveNewsFragment extends SaveBackHandleFragment {
    @ViewInject(R.id.lvSaveList)
    private ListView listView;
    private List<StoriesEntity> newsData;
    private MyApplication app;
    private SaveAdapter<StoriesEntity> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_list,null);
        ViewUtils.inject(this,view);

        app = (MyApplication) getActivity().getApplication();
        getData();  //每次切换到该Fragment是就会重新获取数据
        if (adapter==null){
            adapter = new SaveAdapter<>(newsData,getActivity());
        }
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("storiesEntity", newsData.get(position));
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
    /**
     * 从Application中获取收藏的数据
     */
    public void getData(){
        newsData = app.getNewsSaveData();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
