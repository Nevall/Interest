package com.interest.myapplication.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.interest.myapplication.Utils.Constant;
import com.interest.myapplication.Utils.HttpUtils;
import com.interest.myapplication.Utils.ParseJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import okhttp3.Call;

/**
 * SplashActivity��ҵ����
 */
public class SplashActivityBiz {
	private Context context;
	private ParseJson parse = new ParseJson();

	public SplashActivityBiz(Context context) {
		this.context = context;
	}


	/**
	 * ����Http�����ȡ��������ͼ��
	 * @return 
	 */
	public void loadStratImage(final File file) {
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.START;
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							//����JSON���
							String imageUrl = parse.parseSplashJson(response);
							//����Http�������ر���ͼƬ
							saveImage(imageUrl,file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		else {
//			Toast.makeText(context, "�����޷�����", Toast.LENGTH_SHORT).show();
//		}
	}

	public void saveImage(String imageUrl,final File file){
		if (HttpUtils.isNetworkConnected(context)) {
			try {
				OkHttpUtils.get().url(imageUrl).build().execute(new BitmapCallback() {

					@Override
					public void onResponse(Bitmap bitmap) {
						//����ͼƬ
						saveFile(bitmap, file);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**  
	 * ����Bitmap��ʽͼƬ�ļ�  
	 * @param bm  
	 */    
	public void saveFile(Bitmap bm, File file){ 
		try {
			if (file.exists()) {
				file.delete();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));    
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);    
			bos.flush();    
			bos.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
