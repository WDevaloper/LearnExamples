package com.gavin.asmdemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gavin.asmdemo.db.User;
import com.gavin.asmdemo.db.UserDao;
import com.gavin.asmdemo.db.base.BaseDao;
import com.gavin.asmdemo.db.base.BaseDaoFactory;
import com.gavin.asmdemo.db.base.subdb.BaseDaoSubFactory;
import com.gavin.asmdemo.db.Photo;
import com.gavin.asmdemo.db.PhotoDao;
import com.gavin.asmdemo.db.base.upgrade.UpdateManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
 * <p>
 * <p>
 * 升级：
 * 1、alter table  photo rename to back_photo
 * 2、create table photo(time Text,path Text,lastTime Text)
 * 3、insert into photo(time,path) select time,path from back_photo
 * 4、drop table if exists back_photo
 */
public class DbActivity extends AppCompatActivity {

    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        for (int i = 0; i < 10000; i++) {
            users.add(new User(i, "ooo" + i, "" + i));
        }
    }

    public void createDb(View view) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(
                new File("/data/data/com.gavin.asmdemo/user.db"), null);
        SQLiteDatabase sqLiteDatabase2 = SQLiteDatabase.openOrCreateDatabase(
                new File("/data/data/com.gavin.asmdemo/user2.db"), null);

        StringBuffer sb = new StringBuffer();

        sb.append("create table if not exists user2(name text,age Integer)");
        sqLiteDatabase.execSQL(sb.toString());
        sqLiteDatabase2.execSQL(sb.toString());
    }


    //插入对象
    public void insert(View view) {
//        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
//        Long startTime = System.currentTimeMillis();
//        long insert = userDao.insert(users);
//        long endTime = System.currentTimeMillis() - startTime;
//        Log.e("tag", insert + "  " + endTime + "   baseDao:" + userDao);

//
//        OrderDao orderDao = BaseDaoFactory.getInstance().getBaseDao(OrderDao.class, Order.class);
//        orderDao.insert(new Order(8, "oooooooo"));
//        orderDao.insert(new Order(5, "oooooooo"));
//        orderDao.insert(new Order(2, "oooooooo"));
//        orderDao.insert(new Order(1, "oooooooo"));
//        Log.e("tag", orderDao + "");


        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        userDao.insert(new User(1, "subdb1", "subdb1"));
        userDao.insert(new User(2, "subdb2", "subdb1"));
        userDao.insert(new User(3, "subdb3", "subdb1"));
        userDao.insert(new User(4, "subdb4", "subdb1"));
        userDao.insert(new User(5, "subdb5", "subdb1"));
    }

    // 查询对象
    public void query(View view) {
//        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
//        User where = new User();
//        where.setId(2020);
//        if (baseDao != null) {
//            List<User> user = baseDao.query(where);
//            Log.e("tag", user.size() + " " + user);
//        }
//        Log.e("tag", "   baseDao:" + baseDao);
//
//
//        OrderDao orderDao = BaseDaoFactory.getInstance().getBaseDao(OrderDao.class, Order.class);
//        List<Order> orderList = orderDao.query(new Order());
//        Log.e("tag", orderDao + "   orderList:" + orderList);


        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        User where = new User();
        if (baseDao != null) {
            List<User> user = baseDao.query(where);
            Log.e("tag", user.size() + " " + user);
        }
        Log.e("tag", "   baseDao:" + baseDao);


        PhotoDao photoDao = BaseDaoSubFactory.getInstance().getBaseDao(PhotoDao.class, Photo.class);
        List<Photo> query = photoDao.query(new Photo());

        Log.e("tag", "   photoDao:" + photoDao);
        Log.e("tag", "  photo query:" + query);

    }


    //更新记录
    public void update(View view) {
        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        if (baseDao != null) {
            User user = new User(2020, "2020 arrow", "2020 arrow");
            long startTime = System.currentTimeMillis();
            long update = baseDao.update(user, new User(2020, null, null));
            long endTime = System.currentTimeMillis() - startTime;
            Log.e("tag", update + "   " + endTime);
        }
        Log.e("tag", "   baseDao:" + baseDao);
    }

    //删除记录
    public void delete(View view) {
        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        if (userDao != null) {
            User user = new User();
            long startTime = System.currentTimeMillis();
            long update = userDao.delete(user);
            long endTime = System.currentTimeMillis() - startTime;
            Log.e("tag", update + "   " + endTime);
        }
    }

    int i = 0;

    public void login(View view) {
        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        User user = new User();
        user.setId(i++);
        user.setName("sbudb" + i);
        user.setPassword("9999999999");
        userDao.insert(user);
        Log.e("tag", "" + userDao);
        Toast.makeText(this, "执行成功,正在分库", Toast.LENGTH_SHORT).show();
        fenDb(null);
    }

    //分库
    public void fenDb(View view) {
        Photo photo = new Photo();
        photo.setPath("xx/xx/xx.png");
        photo.setTime(new Date().toString());
        PhotoDao baseDao = BaseDaoSubFactory.getInstance().getBaseDao(PhotoDao.class, Photo.class);
        baseDao.insert(photo);

        Log.e("tag", "" + baseDao);
    }

    //升级,代码升级和xml服务升级
    public void upgradeDb(View view) {
        UpdateManager updateManager = new UpdateManager();
        updateManager.startUpdate(this);
    }
}
