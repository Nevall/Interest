package com.interest.myapplication.view.save;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.interest.myapplication.Entity.bookEntity.BookDetailEntity;
import com.interest.myapplication.Entity.newsEntity.Content;
import com.interest.myapplication.MyApplication;
import com.interest.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Android on 2016/4/6.
 */
public class SaveFragment extends Fragment {
    @ViewInject(R.id.ibSaveBook)
    private ImageButton ibBook;
    @ViewInject(R.id.ibSaveNews)
    private ImageButton ibNews;
    private OnButtonClick onButtonClick;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save,null);
        ViewUtils.inject(this,view);
        setListener();
        return view;
    }

    private void setListener() {
        ibBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.clickButton(0);
            }
        });
        ibNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.clickButton(1);
            }
        });
    }
    public void setOnButtonClick(OnButtonClick onButtonClick){
        this.onButtonClick = onButtonClick;
    }
    public interface OnButtonClick{
        public abstract void clickButton(int position);
    }
}
