package com.github.systemsource;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


public class SystemSourceMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_source_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("tag", "step 0 ");
                Looper.prepare();

                Toast.makeText(SystemSourceMainActivity.this,
                        "run on Thread", Toast.LENGTH_SHORT).show();

                Log.e("tag", "step 1 ");
                Looper.loop();

                Log.e("tag", "step 2 ");

            }
        }).start();
    }
}
