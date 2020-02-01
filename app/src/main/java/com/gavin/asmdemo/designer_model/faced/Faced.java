package com.gavin.asmdemo.designer_model.faced;

import com.gavin.asmdemo.designer_model.faced.impl.DiskCaheImpl;
import com.gavin.asmdemo.designer_model.faced.impl.MemoryCacheImpl;
import com.gavin.asmdemo.designer_model.faced.impl.NetWorkLoaderImpl;

public class Faced {
    private String url;
    private MemoryCache memoryCache;
    private DiskCahe diskCahe;
    private NetWorkLoader loader;

    public Faced(String url) {
        this.diskCahe = new DiskCaheImpl();
        this.memoryCache = new MemoryCacheImpl();
        this.loader = new NetWorkLoaderImpl();
        this.url = url;
    }


    public void loader() {
        memoryCache.findMemory(url);
        diskCahe.findDisk(url);
        loader.findNetWork(url);
    }
}
