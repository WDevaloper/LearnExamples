package com.gavin.asmdemo.db.open;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class SQLiteOpenHelperImpl extends SQLiteOpenHelper {


    //第一个版本的sql
    String FIRST_VERSION_INFO = "create table if not exists testtable( " +
            " id      TEXT     DEFAULT '555'," +
            " name    TEXT     DEFAULT '333'," +
            " age     TEXT     DEFAULT '888')";

    private static final int DATABASE_NEW_VERSION = 1;

    private SQLiteDatabase sqLiteDatabase;
    private static SQLiteOpenHelperImpl mOpenHelper;

    private SQLiteOpenHelperImpl(@Nullable Context context,
                                 @Nullable String name,
                                 @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, DATABASE_NEW_VERSION, null);
    }

    private SQLiteOpenHelperImpl(@Nullable Context context,
                                 @Nullable String name,
                                 @Nullable SQLiteDatabase.CursorFactory factory,
                                 int version,
                                 @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        sqLiteDatabase = this.getWritableDatabase();
    }

    public static SQLiteOpenHelperImpl getInstance(Context context) {
        if (mOpenHelper == null) {
            mOpenHelper = new SQLiteOpenHelperImpl(context, "test.db", null, DATABASE_NEW_VERSION, null);
        }
        return mOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FIRST_VERSION_INFO);
        Log.e("tag", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("tag", oldVersion + " onUpgrade " + newVersion);
    }
}
