package com.gavin.asmdemo.db.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gavin.asmdemo.db.room.entity.User;
import com.gavin.asmdemo.db.room.entity.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}