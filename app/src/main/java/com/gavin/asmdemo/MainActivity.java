package com.gavin.asmdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.gavin.asmdemo.aop.ICheckStatusImpl;
import com.gavin.asmdemo.aop.Test;
import com.gavin.asmdemo.screen_adapter.SecondActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends BaseActivity {


    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main_md);
    }

    private void learnListView() {
        //        mListView = findViewById(R.id.listView);
//        mListView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return 100;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
//            }
//        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void adapter(View view) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.dp_0);
        Log.e("tag", "" + dimensionPixelSize);
//        startActivity(new Intent(this, AdapterActivity.class));
//        getResources().
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        float dimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 10, metrics);
        Log.e("tag", "" + dimension);

    }

    @Test(tag = "test", targetClass = {ICheckStatusImpl.class, SecondActivity.class})
    public void toSecond(View view) {
        ICheckStatusImpl iCheckStatus = new ICheckStatusImpl();
        if (!iCheckStatus.check()) return;
        Log.e("TAG", "toSecond one");
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        Log.e("TAG", "toSecond");
        getResources().getDimensionPixelSize(R.dimen.dp_1);
    }


    // Activity.dispatchTouchEvent --->
    // PhoneWWindow.superDispatchTouchEvent---->
    // DecorView.superDispatchTouchEvent --->
    // ViewGroup.dispatchTouchEvent
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("tag", "onStop");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("tag", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("tag", "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("tag", "onPause");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(String msg) {
        //如果你不是系统应用，也就是你从LauncherActivity启动的App，那么就是没有开启进程
        //那么会通过Zygote创建进程，进而反射调用ActivityThread的main函数，从而开始我们熟悉的ActivityThread的启动流程了
        // LauncherActivity ------> Activity启动过程
        startActivity(new Intent());
    }
}
