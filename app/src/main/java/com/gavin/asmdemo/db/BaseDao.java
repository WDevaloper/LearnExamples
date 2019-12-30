package com.gavin.asmdemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.gavin.asmdemo.db.aninations.DbFiled;
import com.gavin.asmdemo.db.aninations.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BaseDao<T> implements IBaseDao<T> {

    //持有数据库操作的引用
    private SQLiteDatabase sqLiteDatabase;
    //数据表名
    private String tableName;

    //操作数据所对应的类型
    private Class<T> entityClass;


    //标识是否已经初始化
    private boolean mIsInit = false;

    //定义一个缓存Map key是数据库字段名 value是Class成员变量
    private HashMap<String, Field> cacheMap;


    protected boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        if (!mIsInit) {
            //根据传入的class 进行数据表的创建
            DbTable dbTable = entityClass.getAnnotation(DbTable.class);
            if (dbTable != null && !TextUtils.isEmpty(dbTable.value())) {
                tableName = dbTable.value();
            } else {
                tableName = entityClass.getName();
            }

            //数据库没有打开
            if (!sqLiteDatabase.isOpen()) {
                return mIsInit = false;
            }

            //得到创建数据表的sql 串
            String createTableSql = getCreateTableSql();
            sqLiteDatabase.execSQL(createTableSql);
            cacheMap = new HashMap<>();
            initCacheMap();


            return mIsInit = true;
        }

        return mIsInit = false;
    }


    //性能优化
    private void initCacheMap() {
        //取得所有的列名
        String sql = "select * from " + tableName + " limit 1,0";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.close();
        String[] columnNames = cursor.getColumnNames();

        //取得所有的成员变量
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }

        for (String columnName : columnNames) {
            Field fieldColumn = null;
            for (Field field : fields) {
                String fieldName;
                DbFiled dbFiled = field.getAnnotation(DbFiled.class);
                if (dbFiled != null && !TextUtils.isEmpty(dbFiled.value())) {
                    fieldName = dbFiled.value();
                } else {
                    fieldName = field.getName();
                }

                if (columnName.equals(fieldName)) {
                    fieldColumn = field;
                    break;
                }
            }
            if (fieldColumn != null) {
                cacheMap.put(columnName, fieldColumn);
            }
        }
    }

    private String getCreateTableSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ").append(tableName).append("(");

        //反射得到所有的成员变量
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            //优先级最高的是dbFiled，再到属性名称
            DbFiled dbFiled = field.getAnnotation(DbFiled.class);


            if (dbFiled != null && !TextUtils.isEmpty(dbFiled.value())) {
                sb.append(dbFiled.value());
            } else {
                sb.append(field.getName());
            }
            Class<?> fieldType = field.getType();
            if (fieldType == String.class) {
                sb.append(" TEXT,");
            } else if (fieldType == Integer.class) {
                sb.append(" INTEGER,");
            } else if (fieldType == Long.class) {
                sb.append(" BIGINT,");
            } else if (fieldType == Double.class) {
                sb.append(" DOUBLE,");
            } else if (fieldType == byte[].class) {
                sb.append(" BLOB,");
            }
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.replace(sb.length() - 1, sb.length(), ")");
        }
        return sb.toString();
    }

    @Override
    public long insert(T entity) {
        Map<String, String> values = getValues(entity);
        ContentValues contentValues = getContentValues(values);
        return sqLiteDatabase.insert(tableName, null, contentValues);
    }

    private ContentValues getContentValues(Map<String, String> values) {
        ContentValues contentValues = new ContentValues();
        Set<String> keys = values.keySet();
        for (String key : keys) {
            String value = values.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    //数据库对应 字段名 ： 真实值
    private Map<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        for (Field field : cacheMap.values()) {
            try {
                field.setAccessible(true);
                Object obj = field.get(entity);
                if (obj == null) {
                    continue;
                }
                String value = obj.toString();
                String key;
                DbFiled dbFiled = field.getAnnotation(DbFiled.class);
                if (dbFiled != null && !TextUtils.isEmpty(dbFiled.value())) {
                    key = dbFiled.value();
                } else {
                    key = field.getName();
                }


                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }
}
