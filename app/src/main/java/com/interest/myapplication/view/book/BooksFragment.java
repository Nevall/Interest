package com.interest.myapplication.view.book;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.interest.myapplication.Entity.bookEntity.BookDetailEntity;
import com.interest.myapplication.R;
import com.interest.myapplication.Utils.Const;
import com.interest.myapplication.Utils.HttpUtils;
import com.interest.myapplication.model.SearchBiz;
import com.interest.myapplication.widget.MyRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;

public class BooksFragment extends Fragment {
    @ViewInject(R.id.refreshBook)
    private MyRefreshLayout rflBooks;
    @ViewInject(R.id.lvBooks)
    private static ListView lvBooks;
    @ViewInject(R.id.actionButton)
    private FloatingActionButton actionButton;
    /**
     * 返回图书的所有数据源
     */
    private List<BookDetailEntity> books;
    private BookAdapter adapter;
    private List<BookDetailEntity> newBooks;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            rflBooks.setLoading(false);
            rflBooks.setRefreshing(false);
            books = (List<BookDetailEntity>) msg.obj;
            boolean hasMore = true;
            switch (msg.what){
                case Const.MESSAGE_FIRST_UPDATE_BOOK:   //第一次刷新,第二个参数是true
                    newBooks = adapter.updateData(books,true);
                    break;
                case Const.MESSAGE_UPDATE_BOOK: //再次刷新，第二个参数是false
                    newBooks = adapter.updateData(books,false);
                    break;
                case Const.MESSAGE_LOAD_MORE:   //加载以后数据
                    if (hasMore) {
                        hasMore = adapter.loadMoreData();
                    }else{
                        Snackbar snackbar = Snackbar.make(lvBooks, "到底了", Snackbar.LENGTH_SHORT);
                        if(!snackbar.isShownOrQueued()){
                            snackbar.show();
                        }
                    }
                    break;
                case Const.MESSAGE_OTHER_ERROR:
                    Toast.makeText(getActivity(),"加载数据失败，请重新加载",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_novel,null);
        ViewUtils.inject(this,view);
        initialize();
        setListener();
        return view;
    }


    /**
     * 给各个控件初始化，包括发送默认请求返回数据显示界面
     * 给refreshlayout设置childview
     * listview设置adapter
     */
    private void initialize() {

        try {
            AlphaAnimation listViewAnim = new AlphaAnimation(0.0f, 1.0f);
            listViewAnim.setDuration(300);
            LayoutAnimationController lvAnimControl = new LayoutAnimationController(listViewAnim, 1f);  //延迟时间
            lvAnimControl.setOrder(LayoutAnimationController.ORDER_NORMAL); //顺序显示
            lvBooks.setLayoutAnimation(lvAnimControl);  //给LivtView的Item添加淡入淡出效果
            adapter = new BookAdapter(books,getActivity());
            rflBooks.setColorSchemeColors(R.color.colorPrimary,R.color.color50,R.color.color200);    //设置RefreshLayout加载时转动的图标的颜色
            rflBooks.setProgressViewOffset(false, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            lvBooks.setAdapter(adapter);
            getFisrtData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取初始数据
     */
    private void getFisrtData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("tag","最新");
        updateData(map);    //发送请求获取初始数据显示界面
        rflBooks.setRefreshing(true);
    }
    /**
     * 给各种按钮做监听
     */
    private void setListener() {
        /*
        给refreshlayout设置刷新监听,重新加载更多的数据
         */
        rflBooks.setOnRefreshListener(new MyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData(null);
            }
        });
        /*
        给refreshlayout设置加载监听
         */
        rflBooks.setOnLoadListener(new MyRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                loadMoreData();
            }
        });
        /*
        给图书列表设置item事件监听
         */
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!newBooks.isEmpty()) {
                    BookDetailEntity entity = newBooks.get(position);
                    BookDetailActivity.startBookDetailActivity(getActivity(), entity);
                }
            }
        });
        /*
        给actionbutton设置事件监听
         */
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = View.inflate(getActivity(),R.layout.dialog_searchbook,null);
                final TextInputLayout tilName = (TextInputLayout) view.findViewById(R.id.tilName);
                final TextInputLayout tilTag = (TextInputLayout) view.findViewById(R.id.tilTag);
                tilName.setHint("书名");
                tilTag.setHint("标签");
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();
                view.findViewById(R.id.tvSearch).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> map = new HashMap<>();
                        String name = tilName.getEditText().getText().toString();
                        String tag = tilTag.getEditText().getText().toString();
                        if ("".equals(name)&&"".equals(tag)){
                            return;
                        }
                        map.put("q",name);
                        map.put("tag",tag);
                        updateData(map);
                        dialog.dismiss();
                    }
                });
            }
        });

        lvBooks.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        rflBooks.setLoading(true);
        handler.sendEmptyMessage(Const.MESSAGE_LOAD_MORE);
    }
    /**
     * 更新数据
     * @param params    请求参数，如果是获取更多数据则传递null值
     */
    public void updateData(HashMap<String,String> params){
        if (HttpUtils.isNetworkConnected(getActivity())){
            rflBooks.setLoading(true);
            rflBooks.setRefreshing(true);
            if (params!=null){
                SearchBiz.newInstance().searchBook(params,handler);
            }else{
                SearchBiz.newInstance().searchBook(handler);
            }
        }else{
            rflBooks.setRefreshing(false);
            Toast.makeText(getActivity(),"没网了",Toast.LENGTH_SHORT).show();
        }
    }

}
