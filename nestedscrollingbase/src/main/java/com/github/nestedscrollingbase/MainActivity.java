package com.github.nestedscrollingbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingParentHelper;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
