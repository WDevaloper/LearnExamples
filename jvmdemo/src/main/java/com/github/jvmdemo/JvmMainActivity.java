package com.github.jvmdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class JvmMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jvm_main);

        ClassLoader loader = getClass().getClassLoader();
        while (loader!=null){
            System.out.println(loader.getClass().getSimpleName());
            loader =  loader.getParent();
        }

    }
}
