package com.github.glide;

import com.github.glide.resource.BitmapPool;
import com.github.glide.resource.LruBitmapPool;
import com.github.glide.resource.Resource;

public class CacheTest implements Resource.ResourceListener, MemoryCache.ResourceRemoveListener {

    private Key key;
    private ActivieResource activieResource;
    private LruMemoryCache lruMemoryCache;
    private BitmapPool bitmapPool;

    public Resource test() {
        bitmapPool = new LruBitmapPool(10);
        activieResource = new ActivieResource(this);
        lruMemoryCache = new LruMemoryCache(10);
        lruMemoryCache.setResourceRemoveListener(this);


        Resource resource = activieResource.get(key);
        if (resource != null) {
            resource.acquire();
            return resource;
        }


        resource = lruMemoryCache.get(key);
        if (resource != null) {
            //从内存缓存中移除
            lruMemoryCache.remove(key);
            //加入活动缓存，当Bitmap被recycler，加入活动缓存，那么在使用的时候就会崩溃，这里是否使用加入对象池
            resource.acquire();
            activieResource.active(key, resource);
            return resource;
        }


        return null;
    }


    /**
     * 这个资源没有正在使用了
     * 将其从活动资源移除
     * 重新加入到内存缓存中
     */
    @Override
    public void onResourceReleased(Key key, Resource resource) {
        activieResource.deactive(key);
        lruMemoryCache.put(key, resource);
    }


    /**
     * 从内存缓存被动移除   回调放入 复用池
     *
     * @param resource
     */
    @Override
    public void onResourceRemoved(Resource resource) {
        bitmapPool.put(resource.getBitmap());
    }
}
