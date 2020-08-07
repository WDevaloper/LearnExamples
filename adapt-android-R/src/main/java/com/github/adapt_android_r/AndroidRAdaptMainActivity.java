package com.github.adapt_android_r;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.adapt_android_r.sanbox.request.impl.CopyRequest;
import com.github.adapt_android_r.sanbox.FileAccessFactory;
import com.github.adapt_android_r.sanbox.request.impl.FileRequest;
import com.github.adapt_android_r.sanbox.response.FileResponse;
import com.github.adapt_android_r.sanbox.request.impl.ImageRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AndroidRAdaptMainActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androdi_r_adapt_main);
        image = findViewById(R.id.image);
    }

    public void insert(View view) {
        FileRequest fileRequest = new FileRequest(new File("ExternalScopeTest/"));
        fileRequest.setDisplayName("test.txt");
        FileResponse fileResponse =
                FileAccessFactory.create().newCreateFile(this, fileRequest);
        //文件   写入流   文件夹不需要 ， 如果你只想创建文件夹，那么下面的内容就不要执行了
        if (fileResponse.isSuccess()) {
            String data = "我在这里等和你回来";
            OutputStream outputStream = null;
            try {
                outputStream = fileResponse.openOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                bos.write(data.getBytes());
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertImage(View view) {
        ImageRequest imageRequest = new ImageRequest(new File("Images"));
        imageRequest.setDisplayName("test.jpg");
        imageRequest.setMimeType("image/jpeg");
        FileResponse fileResponse = FileAccessFactory.create().newCreateFile(this, imageRequest);
//        不需要更改架构
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.activity);
        try {
            OutputStream outputStream = getContentResolver().openOutputStream(fileResponse.getUri());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "添加图片成功", Toast.LENGTH_SHORT).show();
    }

    public void query(View view) {
        ImageRequest imageRequest = new ImageRequest(new File("Images"));
        imageRequest.setDisplayName("test.jpg");
        FileResponse response = FileAccessFactory.create().query(this, imageRequest);
        if (response.isSuccess()) {
            InputStream inputStream = null;
            try {
                inputStream = response.openInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // 如果不是自己的图片那么需要申请权限
    public void update(View view) {
//        重命名什么文件
        ImageRequest where = new ImageRequest(new File("Images"));
        where.setDisplayName("test.jpg");
        ImageRequest item = new ImageRequest(new File("Images"));
        item.setDisplayName("testapp.jpg");
//        分区存储     难
        FileAccessFactory.create().renameTo(this, where, item);
    }

    // 如果不是自己的图片那么需要申请权限
    public void delete(View view) {
        ImageRequest imageRequest = new ImageRequest(new File("Images"));
        imageRequest.setDisplayName("test.jpg");
        FileAccessFactory.create().delete(this, imageRequest);
    }

    public void copyFile(View view) {
        // Pictures/Images/text.jpg
        ImageRequest srcRequest = new ImageRequest(new File("Images"));
        srcRequest.setDisplayName("test.jpg");
        ImageRequest destRequest = new ImageRequest(new File("Test"));
        // Pictures/Test/TestApp.jpg
        destRequest.setDisplayName("TestApp.jpg");
        CopyRequest<ImageRequest> copyRequest = new CopyRequest<>(srcRequest, destRequest);
        FileResponse copyResponse = FileAccessFactory.create().copyFile(this, copyRequest);
        if (copyResponse.isSuccess()) {
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        }
    }

}
