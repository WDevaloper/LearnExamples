package com.gavin.asmdemo.db;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gavin.asmdemo.R;

import java.io.File;
import java.util.List;


/**
 * 数据库作用：
 * 1、在Android开发中，数据库在中小型APP使用并不广泛，但其对于数据管理的方便性是其他数据存储工具无法取代的;
 * 2、特点：数据集中管理，控制冗余，提高数据的利用率和一致性，有利于程序的开发和维护;
 * <p>
 * 数据库设计三大范式：
 * 1、原子性：字段不接拆分
 * 2、唯一性：唯一标识该字段，比如：id
 * 3、避免冗余性：
 * <p>
 * <p>
 * 数据库框架设计：
 * 1、如何自动创建数据库；
 * 2、如何自动创建数据表；
 * 3、如何让用户使用方便；
 * <p>
 * 将类名 和 属性名 转换为 创建数据表的sql语句
 */
public class DbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
    }

    public void createDb(View view) {
        SQLiteDatabase sqLiteDatabase =
                SQLiteDatabase.openOrCreateDatabase(new File("/data/data/com.gavin.asmdemo/user.db"), null);
        SQLiteDatabase sqLiteDatabase2 =
                SQLiteDatabase.openOrCreateDatabase(new File("/data/data/com.gavin.asmdemo/user2.db"), null);

        StringBuffer sb = new StringBuffer();

        sb.append("create table if not exists user2(name text,age Integer)");
        sqLiteDatabase.execSQL(sb.toString());
        sqLiteDatabase2.execSQL(sb.toString());
    }

    //插入对象
    public void insert(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        if (baseDao != null) {
            baseDao.insert(new User(1, "aaaaaaa", "12345678"));
            baseDao.insert(new User(2, "gg", "12345678"));
            baseDao.insert(new User(2, "bbb", "12345678"));
            baseDao.insert(new User(1, "ggg", "12345678"));
            baseDao.insert(new User(1, "hhhhhh", "12345678"));
            baseDao.insert(new User(1, "vvvvvv", "12345678"));
        }
    }

    // 查询对象
    public void query(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        User where = new User();
        where.setId(2);
        List<User> query = baseDao.query(where);
        for (int i = 0; i < query.size(); i++) {
            Log.e("tag", query.get(i) + "");
        }
    }


    //更新记录
    public void update(View view) {
    }

    //删除记录
    public void delete(View view) {
    }

    //分库
    public void fenDb(View view) {
    }

    //升级
    public void upgradeDb(View view) {
    }
}
