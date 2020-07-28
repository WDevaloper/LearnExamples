package com.wfy.hook_pluginhost;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class HookHostMainActivity extends AppCompatActivity {
    private String TAG = "tag";
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


        try {
            // 第一步拿到被代理对象，即：目标对象
            Class<?> viewClass = Class.forName("android.view.View");
            Field viewListenerInfo = viewClass.getDeclaredField("mListenerInfo");
            viewListenerInfo.setAccessible(true);
            Object listenerInfo = viewListenerInfo.get(button);

            Field mOnClickListenerFiled = listenerInfo.getClass().getDeclaredField("mOnClickListener");
            //通过OnClickListener 接口去生成实现了OnClickListener的代理对象，
            // 然后通过调用代理对象的方法，间接调用OnClickListener的onClick仿
            //返回代理对象
            Object originObject = mOnClickListenerFiled.get(listenerInfo);
            Object proxyInstance =
                    Proxy.newProxyInstance(View.class.getClassLoader(),
                            new Class[]{View.OnClickListener.class},
                            new HookInvocationHandler(originObject)//将目标对象传入HookInvocationHandler，以便操作目标对象
                    );
            // 替换为我们的代理对象，系统调用陪你过也是只会调用我们的代理对象的方法
            mOnClickListenerFiled.set(listenerInfo, proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    class HookInvocationHandler implements InvocationHandler {
        private Object originObject;

        /**
         * @param originObject 原始对象，就是被替换的对象,即目标对象
         */
        public HookInvocationHandler(Object originObject) {
            this.originObject = originObject;
        }

        @Override
        public Object invoke(Object proxy,//真是得代理对象
                             Method method, //被代理对象的方法
                             Object[] args//被代理对象参数
        ) throws Throwable {
            Log.e(TAG, "代理方法被执行 proxy---->" + proxy.getClass().getName());
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }
            if ("onClick".equals(method.getName())) {
                return method.invoke(originObject, args);
            }
            return proxy;
        }
    }
}
