package com.interest.myapplication.Utils;

import android.content.Context;

import java.io.File;

/**
 * Created by Android on 2016/4/8.
 */
public class CacheUtil {
    /**
     * 删除所有缓存
     * @param context
     */
    public static void cleanCache(Context context){
        File cacheDir = context.getCacheDir();
        delectFileByDir(cacheDir);
    }

    /**
     * 删除文件夹中的文件
     * @param file
     */
    public static boolean delectFileByDir(File file){
       if (file!=null&&file.isDirectory()){
           String[] files = file.list();
           for (String cach:files){
               boolean result = delectFileByDir(new File(file,cach));
               if (!result){
                   return false;
               }
           }
       }
        return file.delete();
    }
}
