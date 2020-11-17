package com.github.binderlearn;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

public class CenterAlignImageSpan extends ImageSpan {

    private TextPaint paint = new TextPaint();

    public CenterAlignImageSpan(@NonNull Drawable drawable) {
        super(drawable);
        paint.setColor(Color.parseColor("#FEFEFE"));
        paint.setTextSize(3);
    }


    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);//绘制图片位移一段距离
        b.draw(canvas);
        canvas.drawText("4人团", x, y, paint);
        canvas.restore();
    }
}
