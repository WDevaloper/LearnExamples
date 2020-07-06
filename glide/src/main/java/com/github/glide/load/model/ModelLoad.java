package com.github.glide.load.model;

import android.net.Uri;

import com.github.glide.Key;
import com.github.glide.load.model.data.DataFetcher;

/**
 * 表示数据的来源
 *
 * @param <Model> 表示数据来源
 * @param <Data>  加载成功后数据类型（InputStream byte[] ）
 */
public interface ModelLoad<Model, Data> {

    class LoadData<Data> {
        /**
         * 缓存的key
         */
        Key key;


        public DataFetcher<Data> fetcher;

        public LoadData(Key key, DataFetcher<Data> dataFetcher) {
            this.key = key;
            this.fetcher = dataFetcher;
        }
    }

    /**
     * 判断处理对应model的数据
     *
     * @return
     */
    boolean handle(Uri uri);


    /**
     * 创建加载的数据方式
     *
     * @param model
     */
    LoadData<Data> buildData(Model model);
}
