package com.gavin.asmdemo.screen_adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gavin.asmdemo.BaseActivity;
import com.gavin.asmdemo.R;
import com.gavin.asmdemo.screen_adapter.density.Density;
import com.gavin.asmdemo.screen_adapter.pixel.UiPx2PxScaleAdapt;
import com.gavin.asmdemo.screen_adapter.pixel.UiPx2PxScaleAdaptHelper;

//为了解决对第三方库的适配的影响，我们可以取消Density适配，然后使用自定义像素适配
public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Density.cancelAdaptDensity(this);
        UiPx2PxScaleAdapt.adapt(getApplication()).standardWidth(375).standardHeight(667);
        setContentView(R.layout.activity_second);

        TextView text = findViewById(R.id.text);
        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);
        UiPx2PxScaleAdaptHelper.setUiAdaptPx2PxLayout(text, text1, text2);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "SecondActivity", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
