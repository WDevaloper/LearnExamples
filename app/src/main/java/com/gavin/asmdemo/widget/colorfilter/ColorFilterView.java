package com.gavin.asmdemo.widget.colorfilter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gavin.asmdemo.R;

/**
 * @Describe:
 * @Author: wfy
 */
public class ColorFilterView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;

    public ColorFilterView(Context context) {
        this(context, null);
    }

    public ColorFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * R' = R * mul.R / 0xff + add.R
         * G' = G * mul.G / 0xff + add.G
         * B' = B * mul.B / 0xff + add.B
         */
        //剔除红色 0x00ffff, 0x000000
        //保持原图 0xffffff, 0x000000
        //绿色更亮 0xffffff, 0x000300
//        LightingColorFilter lightingColorFilter = new LightingColorFilter(0xffffff, 0x330000);
//        mPaint.setColorFilter(lightingColorFilter);
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);



        //场景：图像和颜色混合
        // 可以看做：Color.RE创建一张新的图层 而原图看做第二章图层，两张图片叠在一起，得到的是原图上很红的图片
        // PorterDuff.Mode.LIGHTEN表示之前的到的图片的色调进一步调成LIGHTEN，最终得到的效果
//        PorterDuffColorFilter lightingColorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
//        mPaint.setColorFilter(lightingColorFilter);
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);



        //场景：颜色数组以及颜色矩阵图像混合滤镜
        //android会使用颜色矩阵---ColorMatrix,来处理图像的色彩，最后一列为偏移量
//        float[] colorMatrix = {
////                1, 0, 0, 0, 100,   //red
////                0, 1, 0, 0, 100,   //green
////                0, 0, 1, 0, 0,   //blue
////                0, 0, 0, 1, 0    //alpha
////        };
////
////        ColorMatrixColorFilter mColorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
////        mPaint.setColorFilter(mColorMatrixColorFilter);
////        canvas.drawBitmap(mBitmap, 100, 0, mPaint);


        ColorMatrix cm = new ColorMatrix();
        //亮度调节
//        cm.setScale(1,2,1,1);

        //色调调节 0：绿色 1：绿色 2： 蓝色    颜色角度
        cm.setRotate(0, 45);


        //饱和度调节0-无色彩， 1- 默认效果， >1饱和度加强
        cm.setSaturation(10);

        ColorMatrixColorFilter mColorMatrixColorFilter = new ColorMatrixColorFilter(cm);
        mPaint.setColorFilter(mColorMatrixColorFilter);
        canvas.drawBitmap(mBitmap, 100, 0, mPaint);


    }
}
