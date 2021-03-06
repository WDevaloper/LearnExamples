package com.gavin.asmdemo.db.base.subdb;


import androidx.annotation.NonNull;

import com.gavin.asmdemo.db.User;
import com.gavin.asmdemo.db.UserDao;
import com.gavin.asmdemo.db.base.BaseDaoFactory;

import java.io.File;
import java.util.UUID;

/**
 * @Describe: 用于产生私有数据库存放的位置
 * @Author: wfy
 */
public class PrivateDbPathHelper {
    @NonNull
    public static String getPrivateValue(String path) {
        UserDao baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        if (baseDao != null) {
            User currentUser = baseDao.getCurrentUser();
            if (currentUser != null) {
                File file = new File(path);
                if (!file.exists()) file.mkdirs();
                return file.getAbsolutePath() + "/u_" + currentUser.getId() + "_private.db";
            }
        }
        return path + "/u_" + UUID.randomUUID() + "_private";
    }

    public static String getPrivateDbPathById(String id) {
        return BaseDaoSubFactory.sSubDbPath + "/u_" + id + "_private.db";
    }
}
