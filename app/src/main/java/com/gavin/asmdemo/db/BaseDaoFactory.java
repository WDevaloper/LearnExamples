package com.gavin.asmdemo.db;

import android.database.sqlite.SQLiteDatabase;
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

    @Nullable
    public synchronized <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        if (mSqLiteDatabase == null) throw new RuntimeException("数据库没有初始化，请在使用前调用 initDb()");
        BaseDao baseDao = mDaoWeakHashMap.get(entityClass);
        try {
            if (baseDao == null) {
                baseDao = BaseDao.class.newInstance();
                boolean isSuccess = baseDao.init(mSqLiteDatabase, entityClass);
                if (isSuccess) {
                    mDaoWeakHashMap.put(entityClass, baseDao);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return baseDao;
    }
}
