package com.interest.myapplication.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Android on 2016/4/1.
 */
public class MyRefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    private Context context;
    private ListView mListView;
    private boolean isLoading;
    private OnLoadListener onLoadListener;

    public MyRefreshLayout(Context context) {
        super(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView==null){
            getListView();
        }
    }
    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs>0){
            View childView = getChildAt(0);
            if (childView instanceof ListView){
                mListView = (ListView) childView;
                mListView.setOnScrollListener(this);    //给listView设置滑动监听
            }
        }
    }
    /**
     * 设置加载效果
     * @param isLoading
     */
    public void setLoading(boolean isLoading){
        this.isLoading = isLoading;
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        super.setOnRefreshListener(listener);
    }

    /**
     * 指向接口
     * @param onLoadListener
     */
    public void setOnLoadListener(OnLoadListener onLoadListener){
        this.onLoadListener = onLoadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mListView.getLastVisiblePosition()==mListView.getAdapter().getCount()-1&&isLoading==false){
            if (visibleItemCount>1){
                setLoading(true);
                onLoadListener.onLoad();
            }
        }
    }
    /**
     * 加载更多的借口
     */
    public interface OnLoadListener{
        void onLoad();
    }
}
