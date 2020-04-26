package com.gavin.asmdemo.bottom_behavior;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.gavin.asmdemo.R;
import com.gavin.asmdemo.bottom_behavior.dialog.MyBottomDialog;

public class BottomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void click(View view) {
        MyBottomDialog bottomDialog = new MyBottomDialog(this);
        bottomDialog.setContentView(R.layout.bottom_dialog);
        bottomDialog.show();
    }
}
