package com.gavin.asmdemo.db.base.subdb;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gavin.asmdemo.db.base.BaseDao;
import com.gavin.asmdemo.db.base.BaseDaoFactory;

import java.util.WeakHashMap;

/**
 * 一般分库是，根据用户进行分库，每个用户都会有私有数据
 *
 * @Describe: 用于分库的数据库对象，而私有数据会有多个
 * @Author: wfy
 */
public class BaseDaoSubFactory extends BaseDaoFactory {
    static String sSubDbPath = sDbRootPath + "/sub";
    private static final BaseDaoSubFactory instance = new BaseDaoSubFactory();

    //数据库连接池,key:db pth  value : WeakHashMap<Class<?>,BaseDao>
    // WeakHashMap<Class<?>,BaseDao> :防止同一个数据库同一张表存在多个BaseDao,key是entityClass，value是dao
    private WeakHashMap<String, WeakHashMap<Class<?>, BaseDao>> mDbWeakHashMap;

    private BaseDaoSubFactory() {
        super();
        mDbWeakHashMap = new WeakHashMap<>();
    }

    public static BaseDaoSubFactory getInstance() {
        return instance;
    }

    /**
     * 定义一个用于分库的数据库对象
     */
    private SQLiteDatabase subSqLiteDatabase;


    @Override
    @Nullable
    public <DAO extends BaseDao<ENTITY>, ENTITY> DAO getBaseDao(@NonNull Class<DAO> daoClass, Class<ENTITY> entityClass) {
        String databaseValue = PrivateDbPathHelper.getPrivateValue(sSubDbPath);
        BaseDao baseDao = getDao(entityClass, databaseValue);
        try {
            //baseDao为null，有两种情况，1、首次创建数据库；2、首次使用dao
            if (baseDao == null || subSqLiteDatabase == null || !subSqLiteDatabase.isOpen()) {
                synchronized (BaseDaoSubFactory.class) {
                    baseDao = getDao(entityClass, databaseValue);
                    if (baseDao == null) {
                        subSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(databaseValue, null);
                        // 每一张表对应一个dao,为了解决自动创建表，必须一个表对应一个dao
                        baseDao = daoClass.newInstance();
                        boolean isSuccess = baseDao.init(subSqLiteDatabase, entityClass);
                        if (isSuccess) {
                            //从数据库缓存池中获取对应的dao
                            WeakHashMap<Class<?>, BaseDao> daoWeakHashMap = mDbWeakHashMap.get(databaseValue);
                            if (daoWeakHashMap != null) {
                                daoWeakHashMap.put(entityClass, baseDao);
                            }
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


    /**
     * 私有数据库可能存在多个，所以我们需要缓存起来，并且要把统一数据中dao也缓存起来
     *
     * @param entityClass
     * @param databaseValue
     * @param <ENTITY>
     * @return
     */
    @Nullable
    private <ENTITY> BaseDao getDao(Class<ENTITY> entityClass, String databaseValue) {
        //从数据库databaseValue中拿到对应的dao的缓存池
        WeakHashMap<Class<?>, BaseDao> daoWeakHashMap = mDbWeakHashMap.get(databaseValue);
        if (daoWeakHashMap == null) {
            //管理以前的数据库，注意关闭的是私有数据库
            if (subSqLiteDatabase != null && subSqLiteDatabase.isOpen()) {
                subSqLiteDatabase.close();
            }
            //清空以前的dao缓存池
            mDbWeakHashMap.clear();
            // 如果dao的缓存池不存在，那么创建一个dao的缓存池，并加入该数据库缓存池中
            daoWeakHashMap = new WeakHashMap<>();
            mDbWeakHashMap.put(databaseValue, daoWeakHashMap);
        }
        //从获取dao缓存池中获取到指定的dao
        return daoWeakHashMap.get(entityClass);
    }
}
