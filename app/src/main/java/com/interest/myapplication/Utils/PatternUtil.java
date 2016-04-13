package com.interest.myapplication.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android on 2016/4/7.
 */
public class PatternUtil {
    /**
     * 判断是否非负数小数点后两位
     * @param str
     * @return
     */
    public static boolean check(String str){
        Pattern compile = Pattern.compile("^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$");
        Matcher matcher = compile.matcher(str);
        return matcher.find();
    }
}
