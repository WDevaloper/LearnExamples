package com.github.netloader.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.netloader.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class NetWorkMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_main);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().build();
        Api api = retrofit.create(Api.class);
    }

    interface Api {
    }
}
