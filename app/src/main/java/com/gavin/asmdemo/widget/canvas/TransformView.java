package com.gavin.asmdemo.widget.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Describe:
 * @Author: wfy
 */
public class TransformView extends View {

    private Paint mPaint;

    public TransformView(Context context) {
        this(context, null);
    }

    public TransformView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransformView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        平移操作
//        canvas.drawRect(0, 0, 400, 400, mPaint);
//        canvas.translate(50, 50);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(0, 0, 400, 400, mPaint);
//        canvas.drawLine(0, 0, 600, 600, mPaint);


        //缩放操作
//        canvas.drawRect(200, 200, 700, 700, mPaint);
////        canvas.scale(0.5f, 0.5f);
//        canvas.scale(0.5f, 0.5f, 200, 200);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(200, 200, 700, 700, mPaint);

        canvas.drawRect(0, 0, 700, 700, mPaint);

        Matrix matrix = new Matrix();
        matrix.setRotate(45);
        matrix.postTranslate(200, 200);
        canvas.setMatrix(matrix);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, 700, 700, mPaint);

    }
}
