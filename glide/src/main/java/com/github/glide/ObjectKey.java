package com.github.glide;


import java.security.MessageDigest;
import java.util.Objects;

public class ObjectKey implements Key {
    private Object uri;

    public ObjectKey(Object uri) {
        this.uri = uri;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        md.update(getKeyBytes());
    }

    @Override
    public byte[] getKeyBytes() {
        return uri.toString().getBytes();
    }


    /**
     * ArrayList 和 Map
     *
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectKey objectKey = (ObjectKey) o;
        return Objects.equals(uri, objectKey.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }
}
