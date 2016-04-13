package com.interest.myapplication.model;

import okhttp3.Call;
import android.content.Context;
import android.content.Intent;

import com.interest.myapplication.Entity.newsEntity.Content;
import com.interest.myapplication.Utils.Constant;
import com.interest.myapplication.Utils.DBUtil;
import com.interest.myapplication.Utils.HttpUtils;
import com.interest.myapplication.Utils.ParseJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * NewsActivityҵ����
 * @author Android
 */
public class NewsActivityBiz {
	private Context context;
	private Content content;
	private ParseJson parse = new ParseJson();

	public NewsActivityBiz(Context context) {
		this.context = context;
	}

	public void loadNews(final int newsId){
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.CONTENT+newsId;
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {

					@Override
					public void onResponse(String response) {
						//����ݴ��뱾��webCache��ݿ⣬��������
						DBUtil.replaceNewsContent(""+newsId, response);
						//����JSON���
						content = parse.parseContentJson(response);
						//���͹㲥֪ͨ����UI
						Intent intent = new Intent(Constant.ACTION_LOAD_NEWS_CONTENT_SUCCESS);
						intent.putExtra("content", content);
						context.sendBroadcast(intent);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						//��ȡ��ݿ��еĻ������
						loadOfflineData(newsId);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//��ȡ��ݿ��еĻ������
			loadOfflineData(newsId);
		}
	}

	/**
	 * ��ȡ��ݿ��еĻ������
	 */
	private void loadOfflineData(int newsId) {
		try {
			//��ȡ��ݿ��д洢��JSON���
			String json = DBUtil.getNewsContent(""+newsId);
			Intent intent = null;
			if (json != null) {
				//����JSON���
				content = parse.parseContentJson(json);
				//���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_NEWS_CONTENT_OFFLINE_SUCCESS);
				intent.putExtra("content", content);
			}else {
				//û�и�����,���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_NEWS_CONTENT_OFFLINE_FAILURE);
			}
			context.sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
