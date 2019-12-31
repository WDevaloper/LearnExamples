package com.gavin.asmdemo.db.base;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.WeakHashMap;

/**
 * 管理者该数据库的所有表
 */
public class BaseDaoFactory {
    //防止同一个数据库同一张表存在多个BaseDao
    private WeakHashMap<Class<?>, BaseDao> mDaoWeakHashMap;
    private SQLiteDatabase mSqLiteDatabase;
    private String mDbPath;

    //你可以认为一个设备上只能存在一个数据库
    private BaseDaoFactory() {
        this.mDbPath = "data/data/com.gavin.asmdemo/my.db";
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(mDbPath, null);
        mDaoWeakHashMap = new WeakHashMap<>();
    }

    private static class BaseDaoFactoryHolder {
        private static final BaseDaoFactory INSTANCE = new BaseDaoFactory();
    }

    public static BaseDaoFactory getInstance() {
        return BaseDaoFactoryHolder.INSTANCE;
    }


    /**
     * @param daoClass    获取到指定Dao
     * @param entityClass 对应数据表的实体类，也就是表
     * @param <DAO>       获取到指定Dao
     * @param <ENTITY>    对应数据表的实体类
     * @return 具体的实体类
     */
    @Nullable
    public synchronized <DAO extends BaseDao<ENTITY>, ENTITY> DAO
    getBaseDao(@NonNull Class<DAO> daoClass, Class<ENTITY> entityClass) {
        BaseDao baseDao = mDaoWeakHashMap.get(daoClass);
        try {
            if (baseDao == null) {
                baseDao = daoClass.newInstance();
                boolean isSuccess = baseDao.init(mSqLiteDatabase, entityClass);
                if (isSuccess) {
                    mDaoWeakHashMap.put(daoClass, baseDao);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (DAO) baseDao;
    }
}
