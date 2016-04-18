package com.interest.myapplication.Entity.newsEntity;

import com.interest.myapplication.Entity.SaveEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/3/5.
 *
 * 灏佽鐭ヤ箮鏃ユ姤鐨勬柊闂荤殑鏍囬鍜岀収鐗囦俊鎭痵tories绫伙紝鐢ㄤ簬鏄剧ず鍦↙istView涓紝瀹炵幇搴忓垪鍖�
 */
@DatabaseTable(tableName = "storiesEntity")
public class StoriesEntity extends SaveEntity implements Serializable{
    /*stories: [{title: "涓浗鍙や唬瀹跺叿鍙戝睍鍒颁粖澶╂湁涓や釜楂樺嘲锛屼竴涓袱瀹嬩竴涓槑鏈紙澶氬浘锛�,ga_prefix: "052321",
            images: ["http://p1.zhimg.com/45/b9/45b9f057fc1957ed2c946814342c0f02.jpg"],type: 0,id: 3930445},...],*/
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String title;//鏂伴椈鏍囬
    //  private String ga_prefix;
    @DatabaseField
    private List<String> images;//鍥剧墖鍦板潃
    @DatabaseField
    private int type;

    public int getId() {
        return id;
    }

    @Override
    public String getImage() {
        return images.get(0);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }

    public int getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StoriesEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", images=" + images +
                ", type=" + type +
                '}';
    }
}
