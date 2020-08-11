package com.github.adapt_android_r.sanbox;


import com.github.adapt_android_r.sanbox.file.IFile;
import com.github.adapt_android_r.sanbox.file.impl.FileStoreImpl;
import com.github.adapt_android_r.sanbox.file.impl.MediaStoreAccessImp;
import com.github.adapt_android_r.sanbox.uitls.Util;


public class FileAccessFactory {
    public static IFile create() {
        // Android 10.0    10
        if (Util.isAndroidQ()) {
            return MediaStoreAccessImp.getInstance();
        }
        return new FileStoreImpl();
    }


}
