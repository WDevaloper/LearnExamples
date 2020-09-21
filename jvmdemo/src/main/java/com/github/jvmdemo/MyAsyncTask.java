package com.github.jvmdemo;

import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<Integer, Void, Void> {
    @Override
    protected Void doInBackground(Integer... integers) {
        System.out.println("integers = " + integers);
        return null;
    }
}
