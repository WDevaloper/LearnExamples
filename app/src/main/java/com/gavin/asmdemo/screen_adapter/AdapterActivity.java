package com.gavin.asmdemo.screen_adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.gavin.asmdemo.R;
import com.gavin.asmdemo.screen_adapter.pixel.UiPx2PxScaleAdapt;
import com.gavin.asmdemo.screen_adapter.pixel.UiPx2PxScaleAdaptHelper;

/**
 * @Describe: 为了解决对第三方库的适配的影响，我们可以取消Density适配，然后使用自定义像素适配
 * @Author: wfy
 */
public class AdapterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Density.adaptDensity(getApplication(), this);
        //Density.cancelAdaptDensity(MainActivity.this); 取消density适配
        setContentView(R.layout.activity_adapter);




    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        UiPx2PxScaleAdapt.adapt(getApplication()).standardWidth(375).standardHeight(667);
        View contentView = getWindow().getDecorView().findViewById(android.R.id.content);
        UiPx2PxScaleAdaptHelper.setUiAdaptPx2PxContentView(contentView);
    }

}
