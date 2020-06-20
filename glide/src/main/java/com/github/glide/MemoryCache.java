package com.github.glide;


import com.github.glide.resource.Resource;

//内存缓存分别活跃缓存和Lru缓存，所以需要将内存抽象为接口
public interface MemoryCache {
    interface ResourceRemoveListener {
        void onResourceRemoved(Resource resource);
    }

    void setResourceRemoveListener(ResourceRemoveListener removeListener);

    Resource put(Key key, Resource resource);

    Resource remove(Key ke);
}
