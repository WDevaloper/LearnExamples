package com.gavin.asmdemo.db;

import androidx.annotation.NonNull;

import com.gavin.asmdemo.db.base.BaseDao;

import java.util.List;

/**
 * @Describe: 维护用户的公用数据
 * @Author: wfy
 */
public class UserDao extends BaseDao<User> {

    @Override
    public long insert(@NonNull User entity) {
        //查到用户表的所有数据
        long rows = -1L;
        List<User> users = query(new User());
        User where;
        for (User user : users) {
            where = new User();
            if (user.getId().equals(entity.getId())) {
                //如果这个user存在,那么只需要更新数据和登录状态
                where.setId(user.getId());
                entity.setStatus(1);
                rows = update(entity, where);
            } else {
                where.setId(user.getId());
                user.setStatus(0);
                update(user, where);
            }
        }
        if (rows > 0) {
            return rows;
        }
        //插入新用户
        entity.setStatus(1);
        return super.insert(entity);
    }

    /**
     * @return 获取当前登录的用户
     */
    public User getCurrentUser() {
        User where = new User();
        where.setStatus(1);
        List<User> users = query(where);
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }
}
