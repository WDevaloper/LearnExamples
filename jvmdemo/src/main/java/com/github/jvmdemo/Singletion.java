package com.github.jvmdemo;

public class Singletion {
    private static Singletion insatnce;

    private Singletion() {
    }

    public static Singletion getInsatnce() {
        if (null == insatnce) {
            synchronized (Singletion.class) {
                if (null == insatnce) {
                    //Java对象保存在内存中时，由以下三部分组成：
                    //1，对象头
                    //2，实例数据
                    //3，对齐填充字节
                    // new-instance v1, Lcom/github/jvmdemo/Singletion;//创建对象，但是还没有实例化对象数据
                    // invoke-direct {v1}, Lcom/github/jvmdemo/Singletion;-><init>()V  //实例化对象数据
                    // sput-object v1, Lcom/github/jvmdemo/Singletion;->insatnce:Lcom/github/jvmdemo/Singletion;//设置给变量
                    insatnce = new Singletion();
                }
            }
        }
        return insatnce;
    }
}
