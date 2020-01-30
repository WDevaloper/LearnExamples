package com.gavin.asmdemo.designer_dapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gavin.asmdemo.R;
import com.gavin.asmdemo.designer_dapter.faced.Faced;

public class DesignerAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);


        Faced faced = new Faced("");
        faced.loader();
    }
}
