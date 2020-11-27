package com.github.netloader.net;

import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class NetProxyTest {

    @Test
    public void get() throws IOException, InterruptedException {
//        Api api = new NetProxy().createApi(Api.class);
//        Response baiDu = api.getBaiDu();
//        System.out.println(baiDu.body().string());

        //建立连接
        Request request = new Request.Builder()
                .url("ws://echo.websocket.org")
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                webSocket.send("hello world");
                webSocket.send("welcome");
                webSocket.send("哈哈哈，来了兄弟！");
                webSocket.send(ByteString.decodeHex("adefaaaa"));
                webSocket.close(1000, "再见");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                System.out.println("onMessage text = " + text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                System.out.println("onMessage bytes = " + bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("code = " + code + ", reason = " + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("code = " + code + ", reason = " + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                System.out.println("onFailure webSocket = " + webSocket + ", t = " + t.getMessage() + ", response = " + response);
            }
        });

        client.dispatcher().executorService().shutdown();
        Thread.sleep(5000);
    }
}