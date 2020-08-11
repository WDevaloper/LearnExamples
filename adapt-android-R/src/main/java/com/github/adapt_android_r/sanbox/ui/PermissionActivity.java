package com.github.adapt_android_r.sanbox.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.height = 1;
        lp.width = 1;
        window.setAttributes(lp);
        try {
            Intent intent = getIntent();
            IntentSender senderIntent = intent.getParcelableExtra(KEY_INTENT);
            startIntentSenderForResult(senderIntent, REQUEST_CODE, null, 0, 0, 0, null);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (mPermissionCallback != null) {
                mPermissionCallback.onPermissionResult(resultCode);
            }
            finish();
        }
    }

    private static PermissionCallback mPermissionCallback;
    private static String KEY_INTENT = "key_intent";
    private static int REQUEST_CODE = 1000;

    public static void requestPermission(Context context, IntentSender sender, PermissionCallback callback) {
        PermissionActivity.mPermissionCallback = callback;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(KEY_INTENT, sender);
        ActivityCompat.startActivity(context, intent, null);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
