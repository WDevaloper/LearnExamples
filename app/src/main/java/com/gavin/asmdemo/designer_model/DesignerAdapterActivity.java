package com.gavin.asmdemo.designer_model;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gavin.asmdemo.R;
import com.gavin.asmdemo.designer_model.faced.Faced;

public class DesignerAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);


        Faced faced = new Faced("");
        faced.loader();
    }
}
