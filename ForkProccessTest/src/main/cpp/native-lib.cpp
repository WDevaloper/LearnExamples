#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <iostream>
#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <iostream>
#include <string>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <strings.h>
#include <unistd.h>
#include <sys/mman.h>
#include <string>


using namespace std;

#define LOGE(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"tag",FORMAT,##__VA_ARGS__);


extern "C" JNIEXPORT jint JNICALL
Java_com_wfy_myapplication_ForkProcessTestMainActivity_fork(JNIEnv *env, jobject thiz) {

    LOGE("%s%d", "当前进程 native ID= ", getpid());
    LOGE("%s%d", "当前进程的父进程 native ID= ", getppid());

    /**
     * 操作系统会复制一个与父进程完全相同的子进程，虽说是父子关系，但是在操作系统看来，他们更像兄弟关系，
     * 这2个进程共享代码空间，但是数据空间是互相独立的,子进程数据空间中的内容是父进程的完整拷贝，
     * 指令指针也完全相同，子进程拥有父进程当前运行到的位置.
     *
     *
     * 主进程java pid: 3494
     * 当前进程ID= 3494
     * 当前进程的父进程ID= 1472
     * 父进程, pid = 3494
     * 结束了3494
     * 子进程 pid = 3512
     * 结束了3512
     *
     */
    int pid = fork();

    if (pid == -1) return -1;//失败

    if (pid) {
        LOGE("%d%s%d", pid, "  父进程,native pid = ", getpid());
    } else {
        const char *new_name = "mytest";
//        setprogname(new_name);
        //字符串包括结尾符号\0不能超过16，而且这个只是改了proc/pid/stat/\status中的字符串
        LOGE("%d%s%d", pid, "  子进程 native pid = ", getpid());
        LOGE("%s%d%s", "pid=", pid, getprogname())
    }
    return pid;
}



// Binder是什么？
//机制：binder是一种进程间通信机制
//驱动：Binder是一个虚拟物理设备驱动
//应用层：Binder是一个能发起通信的类
//Framework/native: Binder连接了client、service、service manager和Binder驱动程序，形成一套C/S的通信架构

// Binder 是如何做到一次拷贝的？


// Binder 机制是如何跨进程的？


#include <unistd.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <stdlib.h>
#include <fcntl.h>


int8_t *m_prt;
int32_t m_size;
int m_fd;

extern "C"
JNIEXPORT void JNICALL
Java_com_wfy_myapplication_ForkProcessTestMainActivity_mmapTest(JNIEnv *env, jobject thiz,
                                                                jstring buffer_path_) {
    string file = "/sdcard/a.txt";

    m_fd = open(file.c_str(), O_CREAT | O_RDWR, 0666);

    m_size = getpagesize();

    ftruncate(m_fd, m_size);

    m_prt = static_cast<int8_t *>(mmap(0, m_size, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd, 0));


    string data("sddsdssdf");
}