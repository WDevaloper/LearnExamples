package com.gavin.asmdemo.db.subdb;

import android.support.annotation.NonNull;
import android.util.Log;

import com.gavin.asmdemo.db.User;
import com.gavin.asmdemo.db.UserDao;
import com.gavin.asmdemo.db.base.BaseDaoFactory;

import java.util.UUID;

/**
 * @Describe: 用于产生私有数据库存放的位置
 * @Author: wfy
 */
public enum PrivateDatabaseEnum {
    /**
     * 用于产生私有数据库存放的位置
     */
    database("");

    PrivateDatabaseEnum(String value) {
    }

    @NonNull
    public String getValue(String path) {
        UserDao baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        if (baseDao != null) {
            User currentUser = baseDao.getCurrentUser();
            Log.e("tag", "currentUser" + currentUser);
            if (currentUser != null) {
                return path + "/u_" + currentUser.getId() + "_private.db";
            }
        }
        return path + "/u_" + UUID.randomUUID() + "_private";
    }
}
