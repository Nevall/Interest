package com.interest.myapplication.Utils;
/**
 * ���ڸ�ʽ������
 */
public class DateUtils {
	/**
	 * �������ڸ�ʽ
	 */
	public static String convertDate(String date) {
		String result = date.substring(0,4);
		result += "年";
        result += date.substring(4, 6);
        result += "月";
        result += date.substring(6, 8);
        result += "日";
        return result;
	}

}
