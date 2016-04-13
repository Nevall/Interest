package com.interest.myapplication.Entity.newsEntity;

import java.io.Serializable;

/**
 * Created by Android on 2016/3/5.
 *  鏂囩珷鎺ㄨ崘鑰呭疄浣撶被
 */
public class RecommendersEntity implements Serializable{
    /**
     * avatar : http://pic3.zhimg.com/bbb689a7a_m.jpg
     */
    private String avatar;//鏂囩珷鐨勬帹鑽愯�鐨勫ご鍍�

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
