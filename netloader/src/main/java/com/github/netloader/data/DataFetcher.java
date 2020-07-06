package com.github.netloader.data;

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
