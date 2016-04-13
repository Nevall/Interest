package com.interest.myapplication.view.save;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.interest.myapplication.Entity.bookEntity.BookDetailEntity;
import com.interest.myapplication.MyApplication;
import com.interest.myapplication.R;
import com.interest.myapplication.view.book.BookDetailActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Android on 2016/4/6.
 */
public class SaveBookFragment extends SaveBackHandleFragment {
    @ViewInject(R.id.lvSaveList)
    private ListView listView;
    private List<BookDetailEntity> bookData;
    private MyApplication app;
    private SaveAdapter<BookDetailEntity> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_list,null);
        ViewUtils.inject(this,view);
        app = (MyApplication) getActivity().getApplication();
        getData();  //每次切换到该Fragment是就会重新获取数据
        if (adapter==null){
            adapter = new SaveAdapter<>(bookData,getActivity());
        }
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookDetailActivity.startBookDetailActivity(getActivity(),bookData.get(position));
            }
        });
        return view;
    }
    /**
     * 从Application中获取收藏的数据
     */
    public void getData(){
        bookData = app.getBookSaveData();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
