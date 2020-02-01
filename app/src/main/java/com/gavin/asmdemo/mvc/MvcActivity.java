package com.gavin.asmdemo.mvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gavin.asmdemo.R;
import com.gavin.asmdemo.mvc.bean.ImageBean;
import com.gavin.asmdemo.mvc.callback.Callback;

public class MvcActivity extends AppCompatActivity implements Callback {

    private ImageView imageView;
    private final static String PATH = "http://i1.sinaimg.cn/ent/d/2008-06-04/U105P28T3D2048907F326DT20080604225106.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        imageView = findViewById(R.id.bitmap);
    }

    public void click(View view) {
        ImageBean imageBean = new ImageBean();
        imageBean.setRequestPath(PATH);
        new ImageDownLoader().down(this, imageBean);
    }


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case ImageDownLoader.SUCCESS:
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
                case ImageDownLoader.ERROR:
                    Toast.makeText(MvcActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });


    @Override
    public void callback(int resultCode, ImageBean imageBean) {
        Message message = handler.obtainMessage(resultCode);
        message.obj = imageBean.getBitmap();
        handler.sendMessageDelayed(message, 500);
    }
}
