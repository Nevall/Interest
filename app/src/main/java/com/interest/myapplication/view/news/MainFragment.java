package com.interest.myapplication.view.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.interest.myapplication.Entity.newsEntity.Before;
import com.interest.myapplication.Entity.newsEntity.Latest;
import com.interest.myapplication.Entity.newsEntity.StoriesEntity;
import com.interest.myapplication.Entity.newsEntity.TopStoriesEntity;
import com.interest.myapplication.R;
import com.interest.myapplication.Utils.Constant;
import com.interest.myapplication.Utils.DateUtils;
import com.interest.myapplication.Utils.PreUtil;
import com.interest.myapplication.model.MainFragmentBiz;
import com.interest.myapplication.widget.Kanner;
import com.interest.myapplication.widget.MyRefreshLayout;

import java.util.List;


public class MainFragment extends BaseFragment {
	private Latest latest;
	private List<StoriesEntity> storiesEntities;
//	private LinearLayoutManager mLinearLayoutManager;
	private Before before;
	private String date;
	private ListView lvNews;	//新闻的主界面列表
	private MainFragmentBiz biz;
	private BroadcastReceiver receiver;
	private View view;		//MainFragment的View对象
	private MyRefreshLayout rflNews;	//新闻主界面的滑动布局
	private View header;	//广告栏框架
	private NewsAdapter adapter;	//新闻列表界面的适配器
	private Kanner kanner;

	/**
	 * 第一次加载数据
	 */
	@Override
	protected void initData(){
		biz.loadFirst();
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		biz = new MainFragmentBiz(mActivity);	//初始化获取新闻的Biz
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_LOAD_FIRST_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_FIRST_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_FIRST_OFFLINE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_FIRST_OFFLINE_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_BEFORE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_BEFORE_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_BEFORE_OFFLINE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_BEFORE_OFFLINE_FAILURE);
		receiver = new MFBroadcastReceiver();
		LocalBroadcastManager.getInstance(mActivity).registerReceiver(receiver,intentFilter);
		setViews(inflater,container);
		setListeners();
		return view;
	}

	/**
	 * @param inflater
	 * @param container
	 */
	private void setViews(LayoutInflater inflater, ViewGroup container) {
		view = inflater.inflate(R.layout.main_fragment, container, false);
		rflNews = (MyRefreshLayout) view.findViewById(R.id.rflNews);
		lvNews = (ListView) view.findViewById(R.id.lvNews);
		rflNews.setColorSchemeColors(R.color.colorPrimary);
		header = inflater.inflate(R.layout.kanner,lvNews,false);
		kanner = (Kanner) header.findViewById(R.id.kanner);
		lvNews.addHeaderView(header);
		adapter = new NewsAdapter(storiesEntities,getActivity());
		lvNews.setAdapter(adapter);
	}

	/**
	 * 设置监听，包括MyRefreshLayout
	 */
	private void setListeners() {
		/**
		 * MyRefreshLayout刷新时
		 */
		rflNews.setOnRefreshListener(new MyRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				rflNews.setRefreshing(true);
				refresh();
			}
		});
		/**
		 * MyRefreshLayout加载更多时
		 */
		rflNews.setOnLoadListener(new MyRefreshLayout.OnLoadListener() {
			@Override
			public void onLoad() {
				if (biz != null) {
					biz.loadMore(date);
				}
			}
		});
		/**
		 * listview点击监听
		 */
		lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(((StoriesEntity)parent.getAdapter().getItem(position)).getType()==Constant.TOPIC) return;
				else {
					String readSequence = PreUtil.getStringFromDefault(mActivity, "read", "");
					String[] splits = readSequence.split(",");
					StringBuffer sb = new StringBuffer();
					if (splits.length >= 200) {
						for (int i = 100; i < splits.length; i++) {
							sb.append(splits[i] + ",");
						}
						readSequence = sb.toString();
					}
					if (!readSequence.contains(((StoriesEntity)parent.getAdapter().getItem(position)).getId() + "")) {
						readSequence = readSequence + ((StoriesEntity)parent.getAdapter().getItem(position)).getId();
					}
					PreUtil.putStringToDefault(mActivity, "read", readSequence);
					TextView tvTitle = ((TextView) view.findViewById(R.id.tv_title));
					tvTitle.setTextColor(Color.GRAY);
					Intent intent = new Intent(mActivity, NewsActivity.class);
					intent.putExtra("storiesEntity", ((StoriesEntity)parent.getAdapter().getItem(position)));
					mActivity.startActivity(intent);
				}
			}
		});

		/**
		 * 广告栏监听
		 */
		kanner.setOnItemClickListener(new Kanner.OnItemClickListener() {

			@Override
			public void click(View v, TopStoriesEntity entity) {
				StoriesEntity storiesEntity = new StoriesEntity();
				storiesEntity.setId(entity.getId());
				storiesEntity.setTitle(entity.getTitle());
				Intent intent = new Intent(mActivity,NewsActivity.class);
				intent.putExtra("storiesEntity",storiesEntity);
				mActivity.startActivity(intent);
			}
		});
	}
	/**
	 * Fragment转成后台，
	 */
	@Override
	public void onDestroyView() {
		if (receiver!=null) {
			LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(receiver);
		}
		super.onDestroyView();
	}

	/**
	 * 第一次加载布局是获取数据
	 */
	private void updateFirst() {
		date = latest.getDate();
		storiesEntities = latest.getStories();
		kanner.setTopEntities(latest.getTop_stories());
		StoriesEntity topic = new StoriesEntity();
		topic.setType(Constant.TOPIC);
		topic.setTitle("今日热闻");
		storiesEntities.add(0, topic);
		adapter.addList(storiesEntities);
	}

	/**
	 * 加载已经缓存的数据
	 */
	private void updateBefore() {
		if (before == null) {
			return;
		}
		date = before.getDate();
		StoriesEntity topic = new StoriesEntity();
		topic.setType(Constant.TOPIC);
		topic.setTitle(DateUtils.convertDate(before.getDate()));
		storiesEntities = before.getStories();
		storiesEntities.add(0, topic);
		adapter.addList(storiesEntities);

	}

	/**
	 * 加载新的数据
	 */
	public void refresh(){
		rflNews.setRefreshing(true);
		adapter.refresh();
		kanner.refresh();
		biz.loadFirst();
	}

	/**
	 * 广播接收器
	 */
	class MFBroadcastReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			rflNews.setRefreshing(false);
			rflNews.setLoading(false);
			if (Constant.ACTION_LOAD_FIRST_SUCCESS.equals(action)) {
				latest = (Latest) intent.getSerializableExtra("latest");
				if (latest != null) {
					updateFirst();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_BEFORE_SUCCESS.equals(action)
					||Constant.ACTION_LOAD_BEFORE_OFFLINE_SUCCESS.equals(action)) {
				before = (Before) intent.getSerializableExtra("before");
				if (latest != null) {
					updateBefore();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_FIRST_OFFLINE_SUCCESS.equals(action)) {
				Snackbar.make(lvNews, "断网了", Snackbar.LENGTH_SHORT).show();
				latest = (Latest) intent.getSerializableExtra("latest");
				if (latest != null) {
					updateFirst();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_FIRST_OFFLINE_FAILURE.equals(action)
					||Constant.ACTION_LOAD_BEFORE_OFFLINE_FAILURE.equals(action)) {
				Snackbar.make(lvNews, "到底了", Snackbar.LENGTH_SHORT).show();
			}
		}
	}
}
