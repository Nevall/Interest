package com.interest.myapplication.view.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interest.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Android on 2016/3/27.
 */
public class BookDetailFragment extends Fragment {
    @ViewInject(R.id.tvBookInfo)
    private TextView textView;

    public static Fragment newInstance(String content){
        Bundle bundle = new Bundle();
        BookDetailFragment fragment = new BookDetailFragment();
        bundle.putString("title",content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookdetail,null);
        ViewUtils.inject(this,view);
        textView.setText(getArguments().getString("title"));
        return view;
    }
}
