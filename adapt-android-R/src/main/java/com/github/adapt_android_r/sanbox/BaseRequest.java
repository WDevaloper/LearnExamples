package com.github.adapt_android_r.sanbox;

import java.io.File;

public class BaseRequest {
//    Android11   相对路径
    private File file;
//    type   文件的类型
    private String type;


    public BaseRequest(File file) {
        this.file = file;
    }

    public File getFile() {

        return file;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
