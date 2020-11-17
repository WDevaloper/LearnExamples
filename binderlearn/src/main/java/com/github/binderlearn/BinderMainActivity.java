package com.github.binderlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.binderlearn.ipc.BinderActivity;

import java.io.File;
import java.io.FileInputStream;

public class BinderMainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    //private ITestAidlInterface testAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_main);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mmap.txt";
        Uri uri = Uri.parse(path);
        Log.e("tag", uri.toString());
        init(path);

        TextView mTvLabel = findViewById(R.id.mTvLabel);

        SpannableString sbStr = new SpannableString(" 爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺爱傻傻的静安寺");
        Drawable drawable = getResources().getDrawable(R.drawable.pintuan_personnum_type_frame);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ImageSpan imageSpan = new CenterAlignImageSpan(drawable);
        sbStr.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvLabel.setText(sbStr);
    }

    private void test() {
        Log.e("tag", "test");
    }

    public native void init(String path);

    public native void write(String data);

    public native String read();

    public void write(View view) {
        write("mmap");
//        new Thread() {
//            @Override
//            public void run() {
//                long startTime = System.currentTimeMillis();
//                for (int i = 0; i < 10000; i++) {
//                    write("mmap" + i);
//                }
//                long endTime = System.currentTimeMillis();
//                Log.e("tag", "" + endTime);
//            }
//        }.start();
    }

    public void read(View view) {
        String data = read();
        Log.e("tag", data);
    }

    public void jumpIPC(View view) {
        startActivity(new Intent(this, BinderActivity.class));
    }
}
