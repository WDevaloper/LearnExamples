package com.github.glide.disk;

import com.github.glide.Key;

import java.io.File;
import java.io.Writer;

public interface DiskCache {

    interface Writer{
        boolean write(File file);
    }

    File get(Key key);

    void put(Key key, Writer writer);

    void delete(Key key);

    void clear();
}
