package com.example.usercenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.example.usercenter.R.layout.activity_main;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(String msg) {
    }

}
