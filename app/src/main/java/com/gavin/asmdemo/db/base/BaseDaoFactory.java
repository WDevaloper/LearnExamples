package com.gavin.asmdemo.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.util.WeakHashMap;

/**
 * @Describe: 管理者该数据库的所有表, 而且公共数据只有一个,niu
 */
public class BaseDaoFactory {
    //防止同一个数据库同一张表存在多个BaseDao,key是entityClass，value是dao
    //因为公共数据库只存在一个，那么我们之缓存dao，即：dao的缓存池
    private WeakHashMap<Class<?>, BaseDao> mDaoWeakHashMap;
    private SQLiteDatabase mSqLiteDatabase;
    private static String sDbName;
    //数据库主目录
    protected static String sDbRootPath;

    //你可以认为一个设备上只能存在一个数据库
    protected BaseDaoFactory() {
        if (TextUtils.isEmpty(sDbName)) throw new RuntimeException("must call init method");
        if (this.mDaoWeakHashMap == null) {
            this.mDaoWeakHashMap = new WeakHashMap<>();
        }
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
        File file = new File("data/data/" + context.getPackageName());
        if (!file.exists()) {
            file.mkdirs();
        }
        BaseDaoFactory.sDbRootPath = file.getAbsolutePath();
        BaseDaoFactory.sDbName = sDbRootPath + "/" + dbName;
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
            if (baseDao == null || mSqLiteDatabase == null || !mSqLiteDatabase.isOpen()) {
                synchronized (BaseDaoFactory.class) {
                    baseDao = mDaoWeakHashMap.get(entityClass);
                    if (baseDao == null) {
                        baseDao = daoClass.newInstance();
                        this.mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sDbName, null);
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
