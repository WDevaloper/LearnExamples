package com.github.netloader;

import android.net.Uri;

import com.github.netloader.data.DataFetcher;


/**
 * @param <Model> 表示数据来源
 * @param <Data>  加载成功后数据类型（InputStream byte[] ）
 */
public interface ModelLoad<Model, Data> {

    class LoadData<Data> {


        public DataFetcher<Data> fetcher;

        public LoadData(DataFetcher<Data> dataFetcher) {
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
