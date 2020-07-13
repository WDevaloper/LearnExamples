package com.wfy.hook_pluginhost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class HookHostMainActivity extends AppCompatActivity {

    private String TAG = HookHostMainActivity.class.getSimpleName();
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main);

        button = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HookHostMainActivity.this, button.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        //通过OnClickListener 接口去生成实现了OnClickListener的代理对象，然后通过调用代理对象的方法，间接调用OnClickListener的onClick仿

        //返回代理对象
        Object proxyInstance = Proxy.newProxyInstance(View.class.getClassLoader(), new Class[]{View.OnClickListener.class}, new HookInvocationHandler(button));

//        textView.setOnClickListener((View.OnClickListener) proxyInstance);
        try {
            Class<?> viewClass = Class.forName("android.view.View");
            Field viewListenerInfo = viewClass.getDeclaredField("mListenerInfo");
            viewListenerInfo.setAccessible(true);
            Object listenerInfo = viewListenerInfo.get(button);

            Field mOnClickListenerFiled = listenerInfo.getClass().getDeclaredField("mOnClickListener");
            mOnClickListenerFiled.set(listenerInfo, proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class HookInvocationHandler implements InvocationHandler {
        private View mView;

        public HookInvocationHandler(View view) {
            this.mView = view;
        }

        @Override
        public Object invoke(Object proxy,//代理对象
                             Method method, //被代理对象的方法
                             Object[] args//被代理对象参数
        ) throws Throwable {
            if (method != null)
                return method.invoke(this.mView, args);
            return null;
        }
    }
}
