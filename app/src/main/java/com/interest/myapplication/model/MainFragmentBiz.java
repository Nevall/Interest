package com.interest.myapplication.model;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.interest.myapplication.Entity.newsEntity.Before;
import com.interest.myapplication.Entity.newsEntity.Latest;
import com.interest.myapplication.Utils.Constant;
import com.interest.myapplication.Utils.DBUtil;
import com.interest.myapplication.Utils.ParseJson;
import com.interest.myapplication.Utils.HttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


public class MainFragmentBiz {
	private Context context;
	private Latest latest;
	private Before before;
	private ParseJson parse = new ParseJson();

	public MainFragmentBiz(Context context) {
		this.context = context;
	}

	public void loadFirst() {
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.LATESTNEWS;
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							DBUtil.replaceFirstData(response);
							latest = parse.parseFirstJson(response);
							Intent intent = new Intent(Constant.ACTION_LOAD_FIRST_SUCCESS);
							intent.putExtra("latest", latest);
							LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						getFirstOfflineData();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			getFirstOfflineData();
		}
	}

	private void getFirstOfflineData() {
		try {
			String json = DBUtil.getFirstData();
			Intent intent = null;
			if (json != null) {
				latest = parse.parseFirstJson(json);
				intent = new Intent(Constant.ACTION_LOAD_FIRST_OFFLINE_SUCCESS);
				intent.putExtra("latest", latest);
			}else {
				intent = new Intent(Constant.ACTION_LOAD_FIRST_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void loadMore(final String date){
		String url = Constant.BASEURL+Constant.BEFORE+date;
		if (HttpUtils.isNetworkConnected(context)) {
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {

					@Override
					public void onResponse(String response) {
						DBUtil.replaceBeforeData(date, response);
						before = parse.parseBeforeJson(response);
						Intent intent = new Intent(Constant.ACTION_LOAD_BEFORE_SUCCESS);
						intent.putExtra("before", before);
						LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						getBeforeOfflineData(date);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			getBeforeOfflineData(date);
		}
	}

	private void getBeforeOfflineData(String date) {
		try {
			String json = DBUtil.getBeforeData(date);
			Intent intent = null;
			if (json != null) {
				before = parse.parseBeforeJson(json);
				intent = new Intent(Constant.ACTION_LOAD_BEFORE_OFFLINE_SUCCESS);
				intent.putExtra("before", before);
			}else {
				intent = new Intent(Constant.ACTION_LOAD_BEFORE_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
