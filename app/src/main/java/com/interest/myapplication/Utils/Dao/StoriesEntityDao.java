package com.interest.myapplication.Utils.Dao;

import android.content.Context;
import android.database.SQLException;

import com.interest.myapplication.Entity.newsEntity.StoriesEntity;
import com.j256.ormlite.dao.Dao;

import java.util.List;

/**
 * Created by Android on 2016/4/18.
 */
public class StoriesEntityDao {
    private Context context;
    private Dao<StoriesEntity, Integer> storiesEntityIntegerDao;
    private DatabaseHelper helper;

    public StoriesEntityDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            storiesEntityIntegerDao = helper.getDao(StoriesEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一条数据
     */
    public void add(StoriesEntity storiesEntity) {
        try {
            storiesEntityIntegerDao.create(storiesEntity);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据Id查找数据
     */

    public StoriesEntity get(int id) {
        StoriesEntity storiesEntity = null;
        try {
            storiesEntity = storiesEntityIntegerDao.queryForId(id);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return storiesEntity;
    }


    /**
     * 查找所有数据
     */
    public List<StoriesEntity> listAll() {
        List<StoriesEntity> list = null;
        try {
            list = storiesEntityIntegerDao.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 根据Id删除数据
     */
    public void delete(int id) {
        try {
            storiesEntityIntegerDao.deleteById(id);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

}
