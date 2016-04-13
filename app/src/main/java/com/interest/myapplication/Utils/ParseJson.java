package com.interest.myapplication.Utils;


import com.interest.myapplication.Entity.newsEntity.Before;
import com.interest.myapplication.Entity.newsEntity.Content;
import com.interest.myapplication.Entity.newsEntity.Latest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ����JSON�ַ�����
 */
public class ParseJson {
	private Gson gson = new Gson();
	/**
	 * ��������������Ϣ��JSON���
	 * @param json
	 * @return 
	 */
	public Latest parseFirstJson(String json) {
		return gson.fromJson(json, Latest.class);
	}
	/**
	 * ����������Ϣ��JSON���
	 * @param json
	 * @return 
	 */
	public Before parseBeforeJson(String json) {
		return gson.fromJson(json, Before.class);
	}
	/**
	 * �������ž������ݵ�JSON���
	 */
	public Content parseContentJson(String json){
		return gson.fromJson(json, Content.class);
	}


	public String parseSplashJson(String json){
		String imageUrl = null;
		try {
			JSONObject obj = new JSONObject(json);
			imageUrl = obj.getString("img");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return imageUrl;
	}


}
