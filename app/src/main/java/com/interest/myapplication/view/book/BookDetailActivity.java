package com.interest.myapplication.view.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.interest.myapplication.Entity.bookEntity.BookDetailEntity;
import com.interest.myapplication.MyApplication;
import com.interest.myapplication.R;
import com.interest.myapplication.view.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Android on 2016/3/27.
 */
public class BookDetailActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.collapsing_toolbar)
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.sliding_tabs)
    private TabLayout slidingTabs;
    @ViewInject(R.id.ivBookPicture)
    private ImageView ivBook;
    @ViewInject(R.id.fabOther)
    private FloatingActionButton fabOther;
    @ViewInject(R.id.fabShare)
    private FloatingActionButton fabShare;
    @ViewInject(R.id.fabSave)
    private FloatingActionButton fabSave;
    /**
     * 淡入动画
     */
    private Animation showAnimation;
    /**
     * 淡出动画
     */
    private Animation goneAnimation;
    /**
     * Tab的title
     */
    private List<String> titles;
    /**
     * 保存Fragment的集合
     */
    private List<Fragment> framgents = new ArrayList<>();
    private BookDetailEntity book;
    private MyApplication app;

    public static void startBookDetailActivity(Context context, BookDetailEntity bookDetailEntity) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra("book", bookDetailEntity);
        context.startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Book", "onDestroy()");
        framgents = null;
        ShareSDK.stopSDK(this);
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_detail_base);
        ViewUtils.inject(this);
        goneAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_fab_gone);
        showAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_fab_show);

        book = (BookDetailEntity) getIntent().getSerializableExtra("book");
        ShareSDK.initSDK(this);	//初始化shareSDK的配置信息
        initialize();
        setViewPager();
    }
    /**
     * 点击分享按钮后，进行弹出分享界面并分享
     */
    private void share() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(book.getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(book.getAlt().replaceAll("'\'",""));
        // text是分享文本，所有平台都需要这个字段
        oks.setText(book.getAlt_title());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(book.getImage().replaceAll("'\'",""));
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(book.getAlt().replaceAll("'\'",""));
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("想给大家分享一本书");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(book.getAlt().replaceAll("'\'",""));
        // 启动分享GUI
        oks.show(BookDetailActivity.this);
    }
    /**
     * 设置fabButton点击时的动画效果及操作
     */
    private void showOrGoneButton() {
        if (fabShare.getVisibility()==View.VISIBLE){
            fabShare.setAnimation(goneAnimation);
            fabSave.setAnimation(goneAnimation);
            fabShare.startAnimation(goneAnimation);
            fabSave.startAnimation(goneAnimation);
            fabShare.setVisibility(View.GONE);
            fabSave.setVisibility(View.GONE);
        }else if(fabShare.getVisibility()==View.GONE){
            fabShare.setAnimation(showAnimation);
            fabSave.setAnimation(showAnimation);
            fabShare.startAnimation(showAnimation);
            fabSave.startAnimation(showAnimation);
            fabShare.setVisibility(View.VISIBLE);
            fabSave.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 配置主要界面的TabLayout、ViewPager、Fragment
     */
    public void setViewPager() {
        try {
            Picasso.with(this).load(book.getImages().getLarge()).into(ivBook);

            titles = new ArrayList<>();
            titles.add("作品介绍");
            titles.add("认识作者");
            titles.add("目录");

            framgents.add(BookDetailFragment.newInstance(book.getSummary()));
            framgents.add(BookDetailFragment.newInstance(book.getAuthor_intro()));
            framgents.add(BookDetailFragment.newInstance(book.getCatalog()));

            slidingTabs.addTab(slidingTabs.newTab().setText(titles.get(0)));
            slidingTabs.addTab(slidingTabs.newTab().setText(titles.get(1)));
            slidingTabs.addTab(slidingTabs.newTab().setText(titles.get(2)));

            viewPager.setAdapter(new BookAdapter(getSupportFragmentManager()));
            slidingTabs.setupWithViewPager(viewPager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化各控件，包括toolbar和collapsingToobarlayout
     */
    private void initialize() {
        try {
            app = (MyApplication)getApplication();
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            collapsingToolbarLayout.setTitle(book.getTitle());
            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
            collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick({R.id.fabOther,R.id.fabShare,R.id.fabSave})
    public void fabButtonClick(View v){
        switch (v.getId()){
            case R.id.fabOther:
                showOrGoneButton();
                break;
            case R.id.fabShare:
                share();
                showOrGoneButton();
                break;
            case R.id.fabSave:
                showOrGoneButton();
                save();
                break;
        }
    }
    /**
     * 收藏功能，保存到数据库中，和显示在MainActivity的收藏item中
     */
    private void save() {
        try{
            boolean b = app.setBookSaveData(book);
            if (b){
                Toast.makeText(this,"收藏图书成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"已经收藏过了",Toast.LENGTH_SHORT).show();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this,"未知异常收藏失败",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 适配了ViewPager中的Fragment，配合TabLayout实现tab联动
     */
    class BookAdapter extends FragmentPagerAdapter {

        public BookAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return framgents.get(position);
        }

        @Override
        public int getCount() {
            return framgents.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
