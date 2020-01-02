package com.gavin.asmdemo.db.base.upgrade.xml;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.gavin.asmdemo.db.User;
import com.gavin.asmdemo.db.UserDao;
import com.gavin.asmdemo.db.base.BaseDaoFactory;
import com.gavin.asmdemo.db.base.subdb.PrivateDbPathHelper;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @Describe: 数据库升级
 * @Author: wfy
 */
public class UpdateManager {
    //因为我们是分库所以需要得到所有的用户
    private List<User> users = null;

    public void startUpdate(Context context) {
        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        users = userDao.query(new User());
        if (users == null || users.isEmpty()) {
            return;
        }
        //解析xml文件
        UpdateXml updateXml = readDbXml(context);
        if (updateXml == null) {
            return;
        }

        //拿到当前版本信息
        UpdateStep step = analyseUpdateStep(updateXml);
        if (step == null) {
            return;
        }

        //获取用于更新的对象
        List<UpdateDb> updateDbs = step.getUpdateDbs();
        for (User user : users) {
            //得到每个用户的数据库对象
            SQLiteDatabase sqLiteDatabase = getUserDb(user.getId());
            if (sqLiteDatabase != null) {
                for (UpdateDb updateDb : updateDbs) {
                    String sql_rename = updateDb.getSql_rename();
                    String sql_create = updateDb.getSql_create();
                    String sql_insert = updateDb.getSql_insert();
                    String sql_delete = updateDb.getSql_delete();
                    List<String> sqls = Arrays.asList(sql_rename, sql_create, sql_insert, sql_delete);
                    executeSql(sqLiteDatabase, sqls);
                }
            }

        }

    }

    private void executeSql(SQLiteDatabase sqLiteDatabase, List<String> sqls) {
        if (sqls == null || sqls.isEmpty()) {
            return;
        }

        //事务
        sqLiteDatabase.beginTransaction();
        for (String sql : sqls) {
            sql = sql.replace("\r\n", " ");
            sql = sql.replace("\n", " ");
            if (!TextUtils.isEmpty(sql.trim())) {
                sqLiteDatabase.execSQL(sql);
            }
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        Log.e("tag", sqLiteDatabase.getPath() + "升级成功");
    }

    private SQLiteDatabase getUserDb(Integer id) {
        File file = new File(PrivateDbPathHelper.getPrivateDbPathById("" + id));
        if (!file.exists()) {
            Log.e("tag", file.getAbsolutePath() + "数据库不存在");
            return null;
        }
        return SQLiteDatabase.openOrCreateDatabase(file, null);
    }

    private UpdateStep analyseUpdateStep(UpdateXml updateXml) {
        UpdateStep updateStep = null;
        List<UpdateStep> updateSteps = updateXml.getUpdateSteps();
        if (updateSteps == null || updateSteps.size() == 0) {
            return null;
        }

        for (UpdateStep step : updateSteps) {
            if (TextUtils.isEmpty(step.getVersionTo()) || TextUtils.isEmpty(step.getVersionFrom())) {
            } else {
                String[] versionArray = step.getVersionFrom().split(",");
                if (versionArray != null && versionArray.length > 0) {
                    for (int i = 0; i < versionArray.length; i++) {
                        // V002 表示当前版本信息，数据会保存在到sp或者文本文件里
                        // V003 表示 需要升级至版本V003这个版本，应该从服务器获取
                        if ("V002".equalsIgnoreCase(versionArray[i]) &&
                                "V003".equalsIgnoreCase(step.getVersionTo())) {
                            updateStep = step;
                            break;
                        }
                    }
                }
            }
        }
        return updateStep;
    }

    private UpdateXml readDbXml(Context context) {
        InputStream is = null;
        Document document = null;
        try {
            is = context.getAssets().open("update.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (document == null) {
            return null;
        }
        return new UpdateXml(document);
    }
}
