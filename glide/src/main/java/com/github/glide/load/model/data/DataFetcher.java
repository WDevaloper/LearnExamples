package com.github.glide.load.model.data;

/**
 * 表示数据获取的方式
 */
public interface DataFetcher<Data> {
    interface DataFetcherCallback<Data> {
        //数据加载完成
        void onFetcherReady(Data data);

        // 加载失败
        void onLoadFailed(Exception e);
    }

    void loadData(DataFetcherCallback<Data> callback);


    void cancel();
}
