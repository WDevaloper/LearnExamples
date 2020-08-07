package com.github.adapt_android_r.sanbox.request.impl;


import com.github.adapt_android_r.sanbox.request.BaseRequest;


public class CopyRequest<T extends BaseRequest> extends BaseRequest {
    private T srcRequest;
    private T destRequest;

    public CopyRequest(T srcRequest, T destRequest) {
        super(srcRequest.getFile());
        this.srcRequest = srcRequest;
        this.destRequest = destRequest;
    }

    @Override
    public String getUriType() {
        //类型也要保持一直，即：公共目录也要和源文件保持一致
        return srcRequest.getUriType();
    }

    // 公共目录是一致
    @Override
    public String getPath() {
        // 必须保证目标路径必须和源文件的公共目录一直
        // download/test    /sdcard/download/test
        return srcRequest.getPath();
    }

    public BaseRequest getSrcRequest() {
        return srcRequest;
    }

    public BaseRequest getDestRequest() {
        return destRequest;
    }
}
