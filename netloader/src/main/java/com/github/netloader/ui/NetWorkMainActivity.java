package com.github.netloader.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.netloader.R;
import com.github.netloader.net.Api;
import com.github.netloader.net.NetProxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// HTTP
// TCP/IP 分块
// IP
// 以太网


//责任链模式意图：避免请求发送者与接收者耦合在一起，让多个对象都有可能接收请求，将这些对象连接成一条链，并且沿着这条链传递请求，直到有对象处理它为止。
public class NetWorkMainActivity extends AppCompatActivity {

    private ExecutorService threadPool = Executors.newFixedThreadPool(5);
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_main);
        imageView = findViewById(R.id.imageView);
    }

    public void loadImage(View view) {
        threadPool.submit(() -> netTest());
    }

    private void netTest() {
        Log.e("tag", "start====> ");
        Api api = new NetProxy().createApi(Api.class);
        final Response response = api.getBaiDu("Ok", "Http");
        Log.e("tag", "code====> " + response.code());
        if (response.isSuccessful()) {
            InputStream byteStream = response.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(byteStream);
            runOnUiThread(() -> imageView.setImageBitmap(bitmap));
        }
    }

    private void testq() {
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
