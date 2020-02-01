package com.gavin.asmdemo.mvc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gavin.asmdemo.mvc.bean.ImageBean;
import com.gavin.asmdemo.mvc.callback.Callback;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownLoader {
    static final int SUCCESS = 200;
    static final int ERROR = 404;

    void down(Callback callback, ImageBean imageBean) {
        new Thread(new DownLoader(callback, imageBean)).start();
    }

    static final class DownLoader implements Runnable {
        private final Callback callback;
        private final ImageBean imageBean;

        DownLoader(Callback callback, ImageBean imageBean) {
            this.callback = callback;
            this.imageBean = imageBean;
        }

        @Override
        public void run() {
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(imageBean.getRequestPath());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    showUI(SUCCESS, bitmap);
                } else {
                    showUI(ERROR, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showUI(ERROR, null);
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }

        private void showUI(int resultCode, Bitmap bitmap) {
            if (callback != null) {
                imageBean.setBitmap(bitmap);
                callback.callback(resultCode, imageBean);
            }
        }
    }
}
