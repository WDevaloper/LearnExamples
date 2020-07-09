package com.github.plugintaopp;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PlaceholderTaoMainActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeholder_activity_tao_main);
        //注意环境是proxyHostActivity
        Toast.makeText(getActivity(), "这是插件的Activity", Toast.LENGTH_SHORT).show();

        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.startServiceIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceholderService.class);
                startService(intent);
            }
        });


        findViewById(R.id.registerReceiver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.plugin.receiver");
                registerReceiver(new PlaceholderReceiver(), intentFilter);
            }
        });

        findViewById(R.id.sendReceiver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.plugin.receiver");
                sendBroadcast(intent);
            }
        });
    }
}
