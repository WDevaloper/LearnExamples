package com.github.adapt_android_r.sanbox;


import com.github.adapt_android_r.sanbox.file.IFile;
import com.github.adapt_android_r.sanbox.file.impl.FileStoreImpl;
import com.github.adapt_android_r.sanbox.file.impl.MediaStoreAccessImp;
import com.github.adapt_android_r.sanbox.request.BaseRequest;
import com.github.adapt_android_r.sanbox.uitls.UriTypeUtil;


public class FileAccessFactory {
    public static IFile create(BaseRequest baseRequest) {
        // Android 10.0    10
        if (UriTypeUtil.isAndroidQ()) {
            return MediaStoreAccessImp.getInstance();
        }
        return new FileStoreImpl();
    }


}
