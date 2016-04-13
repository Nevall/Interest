package com.interest.myapplication.view.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.interest.myapplication.Entity.newsEntity.Content;
import com.interest.myapplication.Entity.newsEntity.StoriesEntity;
import com.interest.myapplication.MyApplication;
import com.interest.myapplication.R;
import com.interest.myapplication.Utils.Constant;
import com.interest.myapplication.model.NewsActivityBiz;
import com.interest.myapplication.view.BaseActivity;
import com.squareup.picasso.Picasso;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsActivity extends BaseActivity{
	private Toolbar toolbar;
	private ImageView ivTitle;
	private FloatingActionButton fabShare;
	private FloatingActionButton fabSave;
	private FloatingActionButton fabOther;
	private CollapsingToolbarLayout mCollapsingToolbarLayout;
	private StoriesEntity storiesEntity;
	private WebView mWebView;
	private NewsActivityBiz biz;
	private NABroadcastReceiver receiver;
	private Content newsContent;
	private MyApplication app;
	private Animation goneAnimation;
	private Animation showAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_base);
		ShareSDK.initSDK(this);	//初始化shareSDK的配置信息
		app = (MyApplication)getApplication();
		biz = new NewsActivityBiz(this);
		ShareSDK.initSDK(this);	//初始化shareSDK的配置信息
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_LOAD_NEWS_CONTENT_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_NEWS_CONTENT_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_NEWS_CONTENT_OFFLINE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_NEWS_CONTENT_OFFLINE_FAILURE);
		receiver = new NABroadcastReceiver();
		registerReceiver(receiver,intentFilter);
		storiesEntity = (StoriesEntity)getIntent().getSerializableExtra("storiesEntity");
		initView();
		setListener();
		loadData();
	}
	private void initView() {
		fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
		fabOther = (FloatingActionButton) findViewById(R.id.fabOther);
		fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
		goneAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_fab_gone);
		showAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_fab_show);
		ivTitle = (ImageView)findViewById(R.id.iv);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
		mCollapsingToolbarLayout.setTitle(storiesEntity.getTitle());
		mWebView = (WebView)findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setAppCacheEnabled(true);
		mWebView.getSettings().setDatabaseEnabled(true);
	}
	private void setListener() {
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		fabSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrGoneButton();
				save();
			}
		});
		fabOther.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrGoneButton();
			}
		});
		fabShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrGoneButton();
				share();
			}
		});
	}
	private void loadData() {
		biz.loadNews(storiesEntity.getId());
	}
	private void updateNewsContent() {
		Picasso.with(this).load(newsContent.getImage()).into(ivTitle);
		String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
		String html = "<html><head>" + css + "</head><body>" + newsContent.getBody() + "</body></html>";
		html = html.replace("<div class=\"img-place-holder\">", "");
		mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
	}
	/**
	 * 收藏到收藏夹
	 */
	private void save(){
		try{
			boolean b = app.setNewsSaveData(storiesEntity);
			if (b){
				Toast.makeText(this,"收藏话题成功",Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this,"已经收藏过了",Toast.LENGTH_SHORT).show();
			}
		}catch (NullPointerException e){
			e.printStackTrace();
			Toast.makeText(this,"未知异常收藏失败",Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 点击分享按钮后，进行弹出分享界面并分享
	 */
	private void share() {
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(newsContent.getTitle());
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(newsContent.getShare_url().replaceAll("'\'",""));
		// text是分享文本，所有平台都需要这个字段
		oks.setText(newsContent.getTitle());
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImageUrl(newsContent.getImage().replaceAll("'\'",""));
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(newsContent.getShare_url().replaceAll("'\'",""));
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("想给大家分享一篇文章");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(newsContent.getShare_url().replaceAll("'\'",""));
		// 启动分享GUI
		oks.show(NewsActivity.this);
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

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(0, R.anim.slide_out_to_right_from_left);
	}

	@Override
	protected void onDestroy() {
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}


	class NABroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Constant.ACTION_LOAD_NEWS_CONTENT_SUCCESS.equals(action)
					||Constant.ACTION_LOAD_NEWS_CONTENT_OFFLINE_SUCCESS.equals(action)) {
				newsContent = (Content) intent.getSerializableExtra("content");
				if (newsContent != null) {
					updateNewsContent();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_NEWS_CONTENT_OFFLINE_FAILURE.equals(action)) {
				Snackbar.make(mWebView, "网络连接失败", Snackbar.LENGTH_SHORT).show();
			}
		}
	}
}
