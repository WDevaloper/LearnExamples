package com.github.glide.memory;

import com.github.glide.Key;
import com.github.glide.resource.Resource;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActivieResource {
    private Map<Key, ResourceWeakReference> map = new HashMap<>();


    private ReferenceQueue<Resource> referenceQueue;
    private boolean isShutDown = false;
    private Thread mCleanThreadReferenceQueue;
    private final Resource.ResourceListener resourceListener;

    public ActivieResource(Resource.ResourceListener resourceListener) {
        this.resourceListener = resourceListener;
    }

    public void active(Key key, Resource resource) {
        resource.setResourceListener(key, resourceListener);
        map.put(key, new ResourceWeakReference(key, resource, getReferenceQueue()));
    }

    public Resource deactive(Key key) {
        ResourceWeakReference weakReference = map.remove(key);
        if (weakReference != null) {
            return weakReference.get();
        }


        return null;
    }

    public void shutDown(boolean isShutDown) {
        isShutDown = true;
        if (mCleanThreadReferenceQueue != null) {
            mCleanThreadReferenceQueue.interrupt();
            try {
                mCleanThreadReferenceQueue.join(TimeUnit.SECONDS.toMillis(5));
                if (mCleanThreadReferenceQueue.isAlive()) {
                    throw new RuntimeException("Failed join timeout");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ReferenceQueue<? super Resource> getReferenceQueue() {
        if (null == referenceQueue) {
            referenceQueue = new ReferenceQueue<>();
            mCleanThreadReferenceQueue = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isShutDown) {
                            ResourceWeakReference reference =
                                    (ResourceWeakReference) referenceQueue.remove();
                            map.remove(reference.key);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            mCleanThreadReferenceQueue.start();
        }
        return referenceQueue;
    }


    //GC 扫描就被干掉
    static final class ResourceWeakReference extends WeakReference<Resource> {
        final Key key;

        public ResourceWeakReference(Key key, Resource referent, ReferenceQueue<? super Resource> q) {
            super(referent, q);
            this.key = key;
        }
    }

    public Resource get(Key key) {
        ResourceWeakReference weakReference = map.remove(key);
        if (map != null) {
            return weakReference.get();
        }
        return null;
    }
}
