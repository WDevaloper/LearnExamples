package com.gavin.asmdemo.widget.svg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gavin.asmdemo.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MapView extends View {
    private Context context;
    private int[] colorArray = new int[]{0xFF239BD7, 0xFF30A9E5, 0xFF80CBF1, 0xFFFFFFFF};
    private List<ProviceItem> itemList;
    private Paint paint;
    private ProviceItem select;


    public MapView(Context context) {
        super(context);
        init(context);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        itemList = new ArrayList<>();
        mParseThread.start();
    }


    private Thread mParseThread = new Thread() {
        @SuppressLint("RestrictedApi")
        @Override
        public void run() {
            InputStream is = context.getResources().openRawResource(R.raw.china);
            List<ProviceItem> list = new ArrayList<>();
            try {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(is);
                Element documentElement = document.getDocumentElement();
                NodeList nodeList = documentElement.getElementsByTagName("path");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element pathItem = (Element) nodeList.item(i);
                    String pathData = pathItem.getAttribute("android:pathData");
                    Path path = PathParser.createPathFromPathData(pathData);
                    ProviceItem proviceItem = new ProviceItem(path);
                    proviceItem.setDrawColor(colorArray[i % colorArray.length]);
                    list.add(proviceItem);
                }
                itemList = list;
                postInvalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX(), event.getY());

        return super.onTouchEvent(event);
    }

    private void handleTouch(float x, float y) {
        if (itemList == null) {
            return;
        }
        ProviceItem selectItem = null;
        for (ProviceItem proviceItem : itemList) {

            if (proviceItem.isTouch(x, y)) {
                selectItem = proviceItem;
            }
        }
        if (selectItem != null) {
            select = selectItem;
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        获取到当前控件宽高值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemList != null) {
            canvas.save();
            for (ProviceItem proviceItem : itemList) {
                if (proviceItem != select) {
                    proviceItem.drawItem(canvas, paint, false);
                } else {
                    select.drawItem(canvas, paint, true);
                }
            }
            canvas.restore();
        }

    }

}
