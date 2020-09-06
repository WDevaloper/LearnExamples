package com.gavin.asmdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public abstract class BaseActivity extends Activity {
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("");
            }
        }).concatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return Observable.just("");
            }
        });
        Observable.concat(Observable.just(""), Observable.just(""))
                .subscribe();
    }
}
