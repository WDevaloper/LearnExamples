package com.gavin.asmdemo.widget.xfermodel;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * PorterDuff.Mode:
 * 它将所有绘制图形的像素与Canvas中对应位置的像素按照一定规则进行混合，形成新的像素值，从而更新Canvas中最终的像素颜色值
 * 以供18中模式
 * <p>
 * 使用混合模式的地方：
 * 1、ComposeShader   组合着色器
 * 2、Paint.setXfermode  Paint 画笔
 * 3、PorterDuffColorFilter 颜色过滤器
 * <p>
 * <p>
 * 效果永远作用于src源图像区域
 */
public class XfermodeView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }


    /**
     * @param canvas 这个canvas是和我们的窗口关联的，所有需要展示的图形，都必须整合到这个canvas上面
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackgroundColor(Color.GRAY);

        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 100, mPaint);


        // TODO 其实就是要你以绘制的内容作为源图像(src)，以 View 中已有的内容作为目标图像(dst),但是xfermodel永远只作用在src上。

        // Xfermode 指的是你要绘制的内容和 Canvas 的目标位置的内容应该怎样结合计算出最终的颜色。
        // 但通俗地说，其实就是要你以绘制的内容作为源图像，以 View 中已有的内容作为目标图像，
        // 选取一个 PorterDuff.Mode 作为绘制内容的颜色处理方案。

        //混合实际上是两种图层进行混合后的结果，然后将结果合并到view的canvas上的最终结果，也就是相当于一种图形
        //不过混合模式可以由PorterDuff决定最后的两种模式合成的图形
        // 保存图层，实际上是：离屏绘制 ----> 背景会参与混合模式的计算导致出现错误的结果

        // 通过使用离屏绘制，把要绘制的内容单独绘制在缓存区，保证Xfermode的使用不会出现错误的结果
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        // 目标图
        canvas.drawBitmap(createRectBitmapDst(mWidth, mHeight), 0, 0, mPaint);

        // 设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 源图，重叠区域右下角部分
        canvas.drawBitmap(createCircleBitmapSrc(mWidth, mHeight), 0, 0, mPaint);
        // 清楚混合模式,用完及时清除。否则绘制是无法生效的
        mPaint.setXfermode(null);

        //恢复图层
        canvas.restoreToCount(layerId);


        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 50, mPaint);

    }

    /**
     * Canvas 画板
     * Bitmap 画纸，而这张纸，可以随意放到任何的画板(Canvas)上
     * Paint 画笔，就是说Canvas + Bitmap准备好了，Paint可以在上面画画了
     * <p>
     * 这里是构造一张画好的画，然后将需要将这个画放到展厅，让大家欣赏（那就是另一个画板了，即：onDraw中的Canvas）
     * <p>
     * <p>
     * onDraw中的Canvas就是窗口(window)中的Canvas
     */
    private Bitmap createRectBitmapDst(int w, int h) {
        // 使用Bitmap的像素构造Canvas，Canvas只是做到承载，每一个Canvas都必须有相对应的Bitmap来承载内容，我们在Canvas上所作的操作最终都是会写到Bitmap中的。
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        c.drawRect(new RectF(w / 20, h / 3, 2 * w / 3, 19 * h / 20), p);

        return bm;
    }

    private Bitmap createCircleBitmapSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLUE);
        c.drawCircle(w * 2 / 3, h / 3, h / 4, p);
        return bm;
    }
}