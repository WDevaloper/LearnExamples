package com.gavin.asmdemo.widget.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.gavin.asmdemo.R;

/**
 * @Describe:
 * @Author: wfy
 */
public class PathMeasureView extends View {

    private static final String TAG = "tag";
    private Path path = new Path();
    private float[] pos = new float[2];
    // x zou的夹角   ，tangle = tan[1] / tan[0]
    private float[] tan = new float[2];
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap bitmap;
    private Matrix matrix = new Matrix();
    private float mIncrement;

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(6);
        mPaint.setDither(true);


        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(4);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow, options);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mLinePaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mLinePaint);
        //将canvas坐标原点移动到中间
        canvas.translate(getWidth() / 2, getHeight() / 2);


//        Path path = new Path();
//        从当前位置向目标位置画一条直线,x 和 y 是目标位置的坐标，所谓当前位置，即最后一次调用画 Path 的方法的终点位置
//        path.lineTo(0, 200);
//        path.lineTo(200, 200);
//        path.lineTo(200, 0);
//
//        PathMeasure pathMeasure = new PathMeasure();
//        // forceClosed 能够影响路径测量结果
//        pathMeasure.setPath(path, true);
//        Log.e(TAG, "onDraw: forceClosed=true" + pathMeasure.getLength());
//
//        PathMeasure pathMeasure2 = new PathMeasure();
//        pathMeasure2.setPath(path, false);
//        Log.e(TAG, "onDraw: forceClosed=false" + pathMeasure2.getLength());
//
//        PathMeasure pathMeasure1 = new PathMeasure(path,false);
//        Log.e(TAG, "onDraw: PathMeasure(path,false)" + pathMeasure1.getLength());
//
//        path.lineTo(200,-200);
//        //path进行了调整我需要重新调用setPath进行关联
//        pathMeasure1.setPath(path,false);
//        Log.e(TAG, "onDraw: PathMeasure(path,false)" + pathMeasure1.getLength());


//        Path path = new Path();
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//
//        Path dst = new Path();
//        // dst原本的路径不受影响
//        dst.lineTo(-300,-300);
//
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        //截取一部分存入dst中
//        // startWithMoveTo：并且使用moveto保持截取得到的Path第一个点位置不变
//        // startD和stopD：距离抛path起点的长度，
//        pathMeasure.getSegment(200, 1000, dst, false);
//
//
//        canvas.drawPath(path, mPaint);
//
//        mLinePaint.setColor(Color.GREEN);
//        canvas.drawPath(dst, mLinePaint);

        /* nextCounter 跳转到下一条path*/

//        Path path = new Path();
//        path.addRect(-100, -100, 100, 100, Path.Direction.CW);
//        path.addOval(new RectF(-200f, -200f, 200f, 200f),Path.Direction.CW);
//
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//
//        Log.e(TAG, "onDraw: " + pathMeasure.getLength());
//
//        // 跳转到下一条曲线
//        pathMeasure.nextContour();
//        Log.e(TAG, "onDraw: nextCounter" + pathMeasure.getLength());
//
//        canvas.drawPath(path,mPaint);



        /* getPosTan 跳转到下一条path*/


        path.reset();

        // Path.Direction.CCW 会影响Path绘制的方向
        path.addCircle(0, 0, 300, Path.Direction.CCW);
        canvas.drawPath(path, mPaint);

        mIncrement += 0.01;
        if (mIncrement >= 1) {
            mIncrement = 0;
        }

//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        //distance:表示距离path起点的长度
//        //pos:点前点在画布上的位置x、y坐标
//        //tan:当前点在曲线上的方向坐标，通过它可以获取到切线方向与x轴的夹角，tan[1] 表示对边长度，
//        // tan[0] 表示邻边的长度，注意切线肯定是垂直于圆的半径。也就是x、y坐标轴的
//        pathMeasure.getPosTan(pathMeasure.getLength() * mIncrement, pos, tan);
//
//        Log.e(TAG, "onDraw: pos[0] =" + pos[0] + ",pos[1] =" + pos[1]);
//        Log.e(TAG, "onDraw: tan[0] =" + tan[0] + ",tan[1] =" + tan[1]);
//
//        //计算出当前切线与x轴夹角的度数
//        float degrees = (float) ((Math.atan2(tan[1], tan[0]) * 180) / Math.PI);
//        Log.e(TAG, "onDraw: tan " + degrees);
//
//        matrix.reset();
//        // 将图片角度的绘制中心与当前点重合
//        matrix.postRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//        //进行角度旋转
//        matrix.postTranslate(pos[0] - bitmap.getWidth() / 2, pos[1] - bitmap.getHeight() / 2);
//        canvas.drawBitmap(bitmap, matrix, mPaint);

        PathMeasure pathMeasure = new PathMeasure(path, false);
        //将pos信息和tan信息保存在matrix中
        pathMeasure.getMatrix(pathMeasure.getLength() * mIncrement, matrix,
                PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        // 将图片的选中坐标调整到图片的中心位置

        //preTranslate方法的作用是在旋转之间先把图片向上移动图片高度的一半的距离
        /*
          matrix.setScale(interpolatedTime, interpolatedTime);
          matrix.preTranslate(-centerX, -centerY);
          matrix.postTranslate(centerX, centerY);

          preTranslate是指在setScale前,平移,postTranslate是指在setScale后平移

         */
        matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, matrix, mPaint);
        matrix.postTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);

        invalidate();

    }
}
