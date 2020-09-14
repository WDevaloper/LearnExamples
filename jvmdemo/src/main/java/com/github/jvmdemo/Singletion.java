package com.github.jvmdemo;

//singletion.txt
public class Singletion {
    //StoreStore
    //写
    //StoreLoad

    //LoadLoad
    // 读
    // LoadStore
    private volatile static Singletion insatnce;

    private Singletion() {
    }

    public static Singletion getInstance() {
        if (null == insatnce) {
            synchronized (Singletion.class) {
                if (null == insatnce) {
                    //Java对象保存在内存中时，由以下三部分组成：
                    //1，对象头
                    //2，实例数据
                    //3，对齐填充字节


                    //StoreStore
                    // new-instance v1, Lcom/github/jvmdemo/Singletion;//创建对象，堆中已经分配空间，但是还没有实例化对象数据
                    // invoke-direct {v1}, Lcom/github/jvmdemo/Singletion;-><init>()V  //实例化对象数据
                    // sput-object v1, Lcom/github/jvmdemo/Singletion;->insatnce:Lcom/github/jvmdemo/Singletion;//设置给变量
                    //StoreLoad
                    // 由于cpu缓存一致性，造成指令重排
                    // volatile 可以保证线程可见性，但是前提是注意原子性，即一个线程写另一个线程度
                    // i++不属于原子性操作，i++有三部即：先读取i的值，然后写入i的值，在读取

                    insatnce = new Singletion();

                }
            }
        }
        return insatnce;
    }
}
