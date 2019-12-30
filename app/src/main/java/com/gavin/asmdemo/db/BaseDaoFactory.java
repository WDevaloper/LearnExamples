package com.gavin.asmdemo.db;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

public class BaseDaoFactory {
    private static final BaseDaoFactory instance = new BaseDaoFactory();


    public static BaseDaoFactory getInstance() {
        return instance;
    }

    private SQLiteDatabase sqLiteDatabase;
    private String sqlPath = null;

    private BaseDaoFactory() {
        sqlPath = "data/data/com.gavin.asmdemo/ne.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqlPath, null);
    }

    @Nullable
    public <T extends BaseModel> BaseDao<T> getBaseDao(Class<T> entityClass) {
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return baseDao;
    }
}
