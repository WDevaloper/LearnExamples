package com.example.butterknifelib;

import android.view.View;

public abstract class OnDenouncingListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        doClick(view);
    }


    abstract void doClick(View view);
}
