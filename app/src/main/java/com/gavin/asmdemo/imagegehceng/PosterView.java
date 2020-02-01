package com.gavin.asmdemo.imagegehceng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.gavin.asmdemo.R;
import com.gavin.asmdemo.uitls.BitmapUtil;

public class PosterView extends View {

    public PosterView(Context context) {
        super(context);
    }

    public PosterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PosterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getResources().getDimensionPixelSize(R.dimen.dp_207), getResources().getDimensionPixelSize(R.dimen.dp_370));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap mainBitmap =
                BitmapUtil.scaleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.product_poster_test_img),
                        getMeasuredWidth(), getResources().getDimensionPixelSize(R.dimen.dp_320), true);

        Bitmap logobitmap = BitmapUtil.scaleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.product_poster_logo_sysdefault_icon),
                getResources().getDimensionPixelSize(R.dimen.dp_33), getResources().getDimensionPixelSize(R.dimen.dp_31), true);


        Bitmap qritmap = BitmapUtil.scaleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.taeyeon_one),
                getResources().getDimensionPixelSize(R.dimen.dp_42), getResources().getDimensionPixelSize(R.dimen.dp_42), true);


        Bitmap mergeBitmap = mergeBitmap(mainBitmap, logobitmap, qritmap);
        canvas.drawBitmap(mergeBitmap, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
    }


    public Bitmap mergeBitmap(Bitmap mainBitmap, Bitmap logoBitmap, Bitmap qrCodeBitmap) {
        //创建海报
        Bitmap newBitmap = Bitmap.createBitmap(getMeasuredHeight(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);

        canvas.save();
        //绘制白色背景
        canvas.drawColor(getResources().getColor(android.R.color.white));
        canvas.restore();
        canvas.save();
        //绘制海报图
        canvas.drawBitmap(mainBitmap, 0, 0, paint);
        canvas.restore();
        canvas.save();
        //绘制logo
        canvas.drawBitmap(logoBitmap, getResources().getDimension(R.dimen.dp_12), getResources().getDimension(R.dimen.dp_12), paint);
        canvas.restore();
        //绘制二维码
        canvas.save();
        canvas.drawBitmap(qrCodeBitmap, getWidth() / 2f - qrCodeBitmap.getWidth() / 2f,
                mainBitmap.getHeight() - qrCodeBitmap.getHeight() / 2f, paint);
        canvas.restore();

        canvas.save();
        //添加文字   长按识别二维码
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(getResources().getDimension(R.dimen.sp_8));
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
        String text = "扫码查看商品";
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, getWidth() / 2f - rect.width() / 2f,
                mainBitmap.getHeight() + qrCodeBitmap.getHeight() / 2f + rect.height() + 5, paint);
        canvas.restore();


        canvas.save();
        //添加文字   长按识别二维码
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(getResources().getDimension(R.dimen.sp_6));
        paint.setColor(Color.BLACK);
        String text2 = "扫码分利服务由优必上提供技术支持";
        Rect rect2 = new Rect();
        paint.getTextBounds(text2, 0, text2.length(), rect2);
        canvas.drawText(text2, getWidth() / 2f - rect2.width() / 2f,
                getHeight() - rect2.height(), paint);
        canvas.restore();

        return newBitmap;
    }
}
