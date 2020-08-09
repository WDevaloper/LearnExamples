package com.github.adapt_android_r;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.TraceCompat;

import android.Manifest;
import android.app.Activity;
import android.app.RecoverableSecurityException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.adapt_android_r.sanbox.request.impl.WrapperRequest;
import com.github.adapt_android_r.sanbox.FileAccessFactory;
import com.github.adapt_android_r.sanbox.request.impl.FileRequest;
import com.github.adapt_android_r.sanbox.response.FileResponse;
import com.github.adapt_android_r.sanbox.request.impl.ImageRequest;
import com.github.adapt_android_r.sanbox.uitls.MimeTypeUtils;
import com.github.adapt_android_r.sanbox.uitls.Util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class AndroidRAdaptMainActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androdi_r_adapt_main);
        image = findViewById(R.id.image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }

    public void insert(View view) {
        Debug.startMethodTracing(getFilesDir().getAbsolutePath() + File.separator + "app.trace");
        FileRequest fileRequest = new FileRequest(new File("TestExternalScope"));
        fileRequest.setDisplayName("test.txt");
        FileResponse fileResponse =
                FileAccessFactory.create().newCreateFile(this, fileRequest);
        //文件   写入流   文件夹不需要 ， 如果你只想创建文件夹，那么下面的内容就不要执行了
        if (fileResponse.isSuccess()) {
            String data = "我在这里等和你回来le";
            OutputStream outputStream = null;
            try {
                outputStream = fileResponse.openOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                bos.write(data.getBytes());
                bos.close();
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }
        Debug.stopMethodTracing();
    }

    public void insertImage(View view) {
        ImageRequest imageRequest = new ImageRequest(new File("images"));
        imageRequest.setDisplayName("ImageTest.jpg");
        imageRequest.setMimeType("image/jpeg");
        FileResponse fileResponse = FileAccessFactory.create().newCreateFile(this, imageRequest);
        if (!fileResponse.isSuccess()) {
            Toast.makeText(this, "添加图片失败", Toast.LENGTH_SHORT).show();
            return;
        }
        // 不需要更改架构
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
        FileRequest imageRequest = new FileRequest(new File("TestExternalScope"));
        imageRequest.setDisplayName("test.txt");
        FileResponse response = FileAccessFactory.create().query(this, imageRequest);
        if (response.isSuccess()) {
            Log.e("tag", "query: " + response.getOwnerPackageName());
            InputStream inputStream;
            try {
                inputStream = response.openInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String readLine = reader.readLine();
                Log.e("tag", "" + readLine);
                Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 如果不是自己的图片那么需要申请权限
    public void update(View view) {
        FileRequest where = new FileRequest(new File("TestExternalScope"));
        where.setDisplayName("test.txt");
        FileRequest destFile = new FileRequest(new File("TestExternalScope"));
        destFile.setDisplayName("testapp.txt");
//        分区存储     难
        FileResponse fileResponse = FileAccessFactory.create().renameTo(this, where, destFile);

        if (fileResponse.isSuccess()) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }

    // 如果不是自己的图片那么需要申请权限
    public void delete(View view) {
        FileRequest fileRequest = new FileRequest(new File("TestExternalScope"));
        fileRequest.setDisplayName("test.txt");
        FileResponse fileResponse =
                FileAccessFactory.create().delete(this, fileRequest);
        if (fileResponse.isSuccess()) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void copyFile(View view) {
        FileRequest srcRequest = new FileRequest(new File("TestExternalScope"));
        srcRequest.setDisplayName("test.txt");
        FileRequest destRequest = new FileRequest(new File("Test"));
        // Pictures/Test/TestApp.jpg
        destRequest.setDisplayName("test.txt");
        WrapperRequest<FileRequest> copyRequest = new WrapperRequest<>(srcRequest, destRequest);
        FileResponse copyResponse = FileAccessFactory.create().copyFile(this, copyRequest);
        if (copyResponse.isSuccess()) {
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        }
    }


    //能不能查询到其他应用的图片？
    // 可以查询到其他应用的图片，并且可以读取内容，前提是有储存权限
    public void queryImage(View view) {
        ImageRequest imageRequest = new ImageRequest(new File(""));
        imageRequest.setDisplayName("1596136692831.jpg");
        FileResponse response = FileAccessFactory.create().query(this, imageRequest);

        if (!response.isSuccess()) {
            Toast.makeText(this, "没有找到图片", Toast.LENGTH_SHORT).show();
            return;
        }

        InputStream inputStream = null;
        try {
            inputStream = response.openInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            image.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            Util.closeIO(inputStream);
        }
    }

    //能不能修改其他应用的图片名字？
    // 不能修改其他用的文件名 cr.update：RecoverableSecurityException
    public void updateImageName(View view) {
        // 重命名什么文件
        ImageRequest where = new ImageRequest(new File(""));
        where.setDisplayName("1596136692831.jpg");
        ImageRequest item = new ImageRequest(new File(""));
        item.setDisplayName("update.jpg");
        //        分区存储     难
        FileAccessFactory.create().renameTo(this, where, item);
    }

    //能不能删除其他应用的图片名字？
    // 不能删除其他用的文件名 cr.delete：RecoverableSecurityException
    public void deleteImage(View view) {
        try {
            ImageRequest imageRequest = new ImageRequest(new File(""));
            imageRequest.setDisplayName("1596136740273.jpg");
            FileResponse fileResponse = FileAccessFactory.create().delete(this, imageRequest);
            if (!fileResponse.isSuccess()) {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            if (Util.isAndroidQ() && e instanceof RecoverableSecurityException) {
                try {
                    startIntentSenderForResult(
                            ((RecoverableSecurityException) e)
                                    .getUserAction()
                                    .getActionIntent()
                                    .getIntentSender(),
                            REQUEST_DELETE_PERMISSION,
                            null, 0,
                            0, 0, null);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static int REQUEST_DELETE_PERMISSION = 1000;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK &&
                requestCode == REQUEST_DELETE_PERMISSION
        ) {
            // 执行之前的删除逻辑
            deleteImage(null);
        }
    }

    // 能不能复制其他应用的图片？
    // 能复制其他用的文件名 cr.delete：RecoverableSecurityException
    public void copyImage(View view) {
        // Pictures / Images / text.jpg
        ImageRequest srcRequest = new ImageRequest(new File(""));
        srcRequest.setDisplayName("1596136692831.jpg");
        ImageRequest destRequest = new ImageRequest(new File("Test"));
        // Pictures/Test/TestApp.jpg
        destRequest.setDisplayName("TestApp.jpg");
        WrapperRequest<ImageRequest> copyRequest = new WrapperRequest<>(srcRequest, destRequest);
        FileResponse copyResponse = FileAccessFactory.create().copyFile(this, copyRequest);
        if (copyResponse.isSuccess()) {
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void queryAllImage(View view) {

    }

    public void deleteOtherUserImage(View view) {
    }
}
