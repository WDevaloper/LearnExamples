package com.gavin.asmdemo.db.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gavin.asmdemo.db.aninations.DbFiled;
import com.gavin.asmdemo.db.aninations.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @param <T>
 * @Describe: 一个BaseDao对应一张表, 实际上BaseDao只是封装了增删改查的基础操作，如果子类需要拓展直接继承即可
 */
public class BaseDao<T> implements IBaseDao<T> {
    //持有数据库操作的引用
    private SQLiteDatabase mSqLiteDatabase;
    //数据表名
    private String mTableName;
    //操作数据所对应的类型,
    private Class<T> mEntityClass;


    //标识是否已经初始化
    private boolean mIsInit = false;

    //定义一个缓存Map key是数据库字段名 value是Class成员变量
    //为什么需要做这个缓存，第一是为了性能优化，
    // 第二是后面再查询，通过反射把Cursor设置给entity比较方便,
    // 第三是数据库字段和entity进行映射
    private HashMap<String, Field> mCacheMap_DbRecord_EntityField;


    //数据库初始阿虎，需自动创建表
    boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.mSqLiteDatabase = sqLiteDatabase;
        this.mEntityClass = entityClass;
        if (!mIsInit) {
            //根据传入的class 进行数据表的创建
            DbTable dbTable = entityClass.getAnnotation(DbTable.class);
            if (dbTable != null && !TextUtils.isEmpty(dbTable.value())) {
                mTableName = dbTable.value();
            } else {
                mTableName = entityClass.getName();
            }
            //数据库没有打开
            if (!sqLiteDatabase.isOpen()) {
                return mIsInit = false;
            }
            //得到创建数据表的sql 串
            String createTableSql = getCreateTableSql();
            sqLiteDatabase.execSQL(createTableSql);
            mCacheMap_DbRecord_EntityField = new HashMap<>();
            initCacheMap();
            return mIsInit = true;
        }
        return mIsInit = false;
    }


    /**
     * 定义一个缓存Map key是数据库字段名 value是Class成员变量
     * 为什么需要做这个缓存，第一是为了性能优化，第二是后面再查询，
     * 通过反射把Cursor设置给entity比较方便,第三是数据库字段和entity进行映射
     */
    private void initCacheMap() {
        //取得所有的列名
        String sql = "select * from " + mTableName + " limit 1,0";
        Cursor cursor = mSqLiteDatabase.rawQuery(sql, null);
        cursor.close();
        String[] columnNames = cursor.getColumnNames();

        //取得所有的成员变量
        Field[] fields = mEntityClass.getDeclaredFields();
        for (Field field : fields) field.setAccessible(true);

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
                mCacheMap_DbRecord_EntityField.put(columnName, fieldColumn);
            }
        }
    }

    private String getCreateTableSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ").append(mTableName).append("(");

        //反射得到所有的成员变量
        Field[] fields = mEntityClass.getDeclaredFields();
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
            } else if (fieldType == Float.class) {
                sb.append(" Float,");
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
    public long insert(@NonNull T entity) {
        Map<String, String> values = buildValues(entity);
        ContentValues contentValues = buildContentValues(values);
        return mSqLiteDatabase.insert(mTableName, null, contentValues);
    }

    @Override
    public long insert(@NonNull List<T> entitySets) {
        mSqLiteDatabase.beginTransaction();
        long count = 0;
        for (T entity : entitySets) {
            count = insert(entity);
            if (count < 0) break;
        }
        if (count > 0) mSqLiteDatabase.setTransactionSuccessful();
        mSqLiteDatabase.endTransaction();
        return count;
    }

    @Override
    public long update(@NonNull T entity, @NonNull T where) {
        Map<String, String> map = buildValues(entity);
        ContentValues contentValues = buildContentValues(map);

        // id:100  name: "2020"
        Map<String, String> whereArgs = buildValues(where);
        // where id = 100 and name = "2020"
        Condition condition = new Condition(whereArgs);
        return mSqLiteDatabase.update(mTableName, contentValues, condition.whereCause, condition.whereArgs);
    }

    @Override
    public long delete(@NonNull T where) {
        // id:100  name: "2020"
        Map<String, String> whereArgs = buildValues(where);
        // where id = 100 and name = "2020"
        Condition condition = new Condition(whereArgs);
        return mSqLiteDatabase.delete(mTableName, condition.whereCause, condition.whereArgs);
    }

    @Override
    public List<T> query(@NonNull T where) {
        return query(where, null, null, null);
    }

    @Override
    public List<T> query(@NonNull T where, String orderBy, Integer startIndex, Integer limit) {
        Map<String, String> values = buildValues(where);

        //select * from  mTableName limit 0,1
        String limitString = null;
        if (startIndex != null && limit != null) {
            limitString = startIndex + " , " + limit;
        }
        // String selections = "where 1 = 1 and id = ? and name = ?"
        // String[] selectionArgs = new String[]{1,"2222"}
        // select * from mTableName whwre id = 1 and name = "2222"


        Condition condition = new Condition(values);
        Cursor cursor = mSqLiteDatabase.query(mTableName,
                null, condition.whereCause,
                condition.whereArgs, null, null, orderBy, limitString);
        return getResult(cursor, where);
    }


    /**
     * 根据where类型创建对象，并通过cacheMap获取到该对象的Field，然后使用反射赋值:
     * columnName-> columnIndex -> Field.set(xx,xx)
     *
     * @param cursor
     * @param where
     * @return
     */
    private List<T> getResult(Cursor cursor, @NonNull T where) {
        List<T> list = new ArrayList<>();
        T item;
        while (cursor.moveToNext()) {
            try {
                //user=new User(),user.setId(cursor.getId())
                item = (T) where.getClass().newInstance();
                list.add(item);
                //赋值给entity对象
                for (Map.Entry<String, Field> entryMap : mCacheMap_DbRecord_EntityField.entrySet()) {
                    // 获取列名
                    String columnName = entryMap.getKey();
                    //以列名拿到列名在游标中的位置
                    int columnIndex = cursor.getColumnIndex(columnName);
                    //获取成员变量的类型
                    Field field = entryMap.getValue();
                    Class<?> fieldType = field.getType();
                    if (columnIndex != -1) {
                        if (fieldType == String.class) {
                            field.set(item, cursor.getString(columnIndex));
                        } else if (fieldType == Integer.class) {
                            field.set(item, cursor.getInt(columnIndex));
                        } else if (fieldType == Long.class) {
                            field.set(item, cursor.getLong(columnIndex));
                        } else if (fieldType == Double.class) {
                            field.set(item, cursor.getDouble(columnIndex));
                        } else if (fieldType == Float.class) {
                            field.set(item, cursor.getFloat(columnIndex));
                        } else if (fieldType == byte[].class) {
                            field.set(item, cursor.getDouble(columnIndex));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }


    /**
     * 将entity的字段名和对应数据库的字段名和值构造条件:
     * <p>
     * SqLiteDatabase -> selection 和 selectionArgs 即：
     * String selections = "where 1 = 1 and id = ? and name = ?"
     * String[] selectionArgs = new String[]{1,"2222"}
     * <p>
     * select * from mTableName whwre id = 1 and name = "2222"
     */
    private class Condition {
        private String whereCause;
        private String[] whereArgs;

        Condition(Map<String, String> whereMap) {
            ArrayList<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            sb.append("1 = 1");//为了解决，拼接时多出and
            Set<String> keySet = whereMap.keySet();//获取所有的字段
            for (String key : keySet) {
                String value = whereMap.get(key);
                if (value != null) {
                    sb.append(" and ").append(key).append(" = ?");
                    list.add(value);
                }
            }
            this.whereArgs = list.toArray(new String[0]);
            this.whereCause = sb.toString();
        }
    }

    /**
     * 将数据库的字段名和值构造ContentValues
     *
     * @param values 数据库对应字段名 ： 真实值
     * @return ContentValues
     */
    private ContentValues buildContentValues(Map<String, String> values) {
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

    //数据库对应字段名 ： 真实值
    private Map<String, String> buildValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        for (Field field : mCacheMap_DbRecord_EntityField.values()) {
            try {
                field.setAccessible(true);

                // 获取Field的值
                Object obj = field.get(entity);
                if (obj == null) continue;
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
