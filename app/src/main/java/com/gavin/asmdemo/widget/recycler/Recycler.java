package com.gavin.asmdemo.widget.recycler;

import android.view.View;

import java.util.Stack;

public class Recycler {
    private Stack<View>[] views;

    public Recycler(int typeNum) {
        views = new Stack[typeNum];
        for (int i = 0; i < typeNum; i++) {
            views[i] = new Stack<>();
        }
    }

    public void put(View view, int type) {
        views[type].push(view);
    }

    public View get(int type) {
        return views[type].pop();
    }
}
