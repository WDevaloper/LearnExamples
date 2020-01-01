package com.gavin.asmdemo.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.WeakHashMap;

/**
 * 管理者该数据库的所有表
 */
public class BaseDaoFactory {
    //防止同一个数据库同一张表存在多个BaseDao,key是entityClass，value是dao
    protected WeakHashMap<Class<?>, BaseDao> mDaoWeakHashMap;
    private SQLiteDatabase mSqLiteDatabase;
    private static String sDbName;
    protected static String sPathName;

    //你可以认为一个设备上只能存在一个数据库
    private BaseDaoFactory() {
        if (TextUtils.isEmpty(sDbName)) throw new RuntimeException("must call init method");
        this.mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sDbName, null);
        this.mDaoWeakHashMap = new WeakHashMap<>();
    }

    private static class BaseDaoFactoryHolder {
        private static final BaseDaoFactory INSTANCE = new BaseDaoFactory();
    }

    public static BaseDaoFactory getInstance() {
        return BaseDaoFactoryHolder.INSTANCE;
    }

    /**
     * 使用前必须初始化
     *
     * @param dbName data/data/xx.xx/my.db
     */
    public static void init(Context context, String dbName) {
        BaseDaoFactory.sPathName = "data/data/" + context.getPackageName() + "/";
        BaseDaoFactory.sDbName = sPathName + dbName;
    }

    /**
     * @param daoClass    获取到指定Dao
     * @param entityClass 对应数据表的实体类，也就是表
     * @param <DAO>       获取到指定Dao
     * @param <ENTITY>    对应数据表的实体类
     * @return 具体的实体类
     * <p>
     * create table if not exists order(desc TEXT,id INTEGER)
     * create table if not exists tb_user(id INTEGER,name TEXT,password TEXT)
     */
    @Nullable
    public <DAO extends BaseDao<ENTITY>, ENTITY> DAO getBaseDao(@NonNull Class<DAO> daoClass, Class<ENTITY> entityClass) {
        // 每一张表对应一个dao
        BaseDao baseDao = mDaoWeakHashMap.get(entityClass);
        try {
            if (baseDao == null) {
                synchronized (BaseDaoFactory.class) {
                    baseDao = mDaoWeakHashMap.get(entityClass);
                    if (baseDao == null) {
                        baseDao = daoClass.newInstance();
                        // 每一张表对应一个dao,为了解决自动创建表，必须一个表对应一个dao
                        boolean isSuccess = baseDao.init(mSqLiteDatabase, entityClass);
                        if (isSuccess) {
                            mDaoWeakHashMap.put(entityClass, baseDao);
                        }
                    }
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
