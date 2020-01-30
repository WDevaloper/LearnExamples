package com.gavin.asmdemo.designer_dapter.faced;

import com.gavin.asmdemo.designer_dapter.faced.impl.DiskCaheImpl;
import com.gavin.asmdemo.designer_dapter.faced.impl.MemoryCacheImpl;
import com.gavin.asmdemo.designer_dapter.faced.impl.NetWorkLoaderImpl;

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
