package com.github.netloader.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.github.netloader.R;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


// HTTP
// TCP/IP 分块
// IP
// 以太网
public class NetWorkMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_main);
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("www.baidu.com", "sha256//558pd1Y5Vercv1ZoSqOrJWDsh9sTMEolM6T8csLucQ=")//公钥
                .add("www.baidu.com", "sha256/IQBnNBEiFuhj+8x6X8XLgh01V9Ic5/V3IRQLNFFc7v4=")//公钥
                .add("www.baidu.com", "sha256/K87oWBWM9UZfyddvDfoxL+8lpNyoUB2ptGtn0fv6G2Q=")//公钥
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .authenticator(new Authenticator() {
                    @Nullable
                    @Override
                    public Request authenticate(@Nullable Route route, Response response) throws IOException {
                        Log.e("tag", "authenticate>>> " + Thread.currentThread().getName() + "   ");
                        return response.request().newBuilder()
                                .header("Proxy-Authorization", "sddsfdfdgdgdgd")
                                .build();
                    }
                })
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        Log.e("tag", "saveFromResponse>>>>" + httpUrl.toString());
                        for (Cookie cookie : list) {
                            Log.e("tag", "cookie" + cookie.name() + "  " + cookie.value());
                        }
                    }


                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        Log.e("tag", "loadForRequest>>>" + httpUrl.toString());
                        return Collections.emptyList();
                    }
                })
                .build();
        Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .build();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String respData = Objects.requireNonNull(response.body()).string();
                        Log.e("tag", "" + respData);
                    }
                });
    }
}
