package com.github.adapt_android_r;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AndroidRAdaptMainActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androdi_r_adapt_main);
        image = findViewById(R.id.image);
    }

    /**
     * DISPLAY_NAME：图片的名字，需要包含后缀名。在这里，我们使用的是当前的时间戳命名
     * MIME_TYPE：文件的mime类型。在这里，我们使用的是image/jpeg
     * RELATIVE_PATH、DATA：文件的存储路径。在Android 10中，新增了RELATIVE_PATH，
     * 它表示文件存储的相对路径，可选值其实就是Environment里面那堆，比如Pictures、Music等.
     * DATA这个字段是Android 10以前使用的字段，在Android 10中已经废弃，但为了兼容老版本系统， 我们还是要用。这个字段需要文件的绝对路径。
     */
    String displayName = "1.jpg";//文件名称

    public void insert(View view) throws IOException {
        ContentValues values = new ContentValues();
        //设置文件名
        values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
            //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
            //RELATIVE_PATH是相对路径不是绝对路径
            //DCIM 是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，目录不能是不存在的的目录名字
            String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "ScopedTest";
            Log.e("tag", ">>>>>>>>>>>>>>>>" + relativePath);
            values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
            //contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Music/signImage");
        } else {
            // 如果图片或者目录存在就会报错： UNIQUE constraint failed: files._data
            //contentValues.put(MediaStore.Images.Media.DATA, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .getPath() + File.separator + "ScopedTest";
            File file = new File(path);
            if (!file.exists()) file.mkdirs();
            // 不能这样搞，这样是会报错，把创建文件的操作交给MediaStore
            // file = new File(path, displayName);
            // if (!file.exists()) file.createNewFile();
            Log.e("tag", ">>>>>>>>>>>>>>>>" + file.getPath());
            values.put(MediaStore.Images.Media.DATA, file.getPath() + File.separator + displayName);
        }

        try {
            Uri insertUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            OutputStream outputStream = getContentResolver().openOutputStream(insertUri, "rw");
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.base);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query(View view) throws FileNotFoundException {
        String pathKey = "";
        String pathValue = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            pathKey = MediaStore.MediaColumns.DATA;//_data = /sdcard/ScopedTest1.jpg
        } else {
            pathKey = MediaStore.MediaColumns.RELATIVE_PATH;
        }
        pathValue = getAppPicturePath();// /sdcard/ScopedTest/

        Log.e("tag", pathKey + "  = " + pathValue);
        String[] projection = {MediaStore.Images.Media._ID};
        String selection = pathKey + " LIKE ?";
        String[] args = {"%" + pathValue + "%"};

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, args, null);
        Log.e("tag", "" + cursor.getCount());
        while (cursor.moveToNext()) {
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(0));
            Log.e("tag", "" + uri);
        }
    }

    private String getAppPicturePath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + File.separator + "ScopedTest";
        } else {


            // relative path
            return Environment.DIRECTORY_PICTURES + File.separator + "ScopedTest";
        }
    }

    private void query1() throws FileNotFoundException {
        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=? ";
        String[] args = {displayName};
        String[] projection = {MediaStore.Images.Media._ID};
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, args, null);
        if (cursor.moveToFirst()) {
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(0));
            InputStream is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            image.setImageBitmap(bitmap);
        }
    }

    // 如果不是自己的图片那么需要申请权限
    public void update(View view) {

    }

    // 如果不是自己的图片那么需要申请权限
    public void delete(View view) {

    }
}
