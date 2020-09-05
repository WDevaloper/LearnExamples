package com.github.skin_core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SkinMainActivity extends AppCompatActivity {

    // LayoutInflater.from(this); 有主题
    // LayoutInflater.from(getApplicationContext()); 无主题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_main);
        getSupportFragmentManager().beginTransaction().add(new SkinFragment(), "fragment").commit();

        String resourcePackageName = getResources().getResourceName(R.style.AppTheme);
        int appTheme = getResources().getIdentifier("AppTheme", "style", "com.github.skin_core");

        Log.e("tag", appTheme + "  onCreate  " + resourcePackageName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.e("tag", "SkinMainActivity onCreateView");
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attributeName = attrs.getAttributeName(i);
            int attributeNameResource = attrs.getAttributeNameResource(i);
            String attributeValue = attrs.getAttributeValue(i);
            // Log.e("tag", "attributeName >>> " + attributeName + "   attributeValue >>>> " + attributeValue);
        }

        return super.onCreateView(name, context, attrs);
    }
}
