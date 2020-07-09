#include <jni.h>
#include <string>
#include <unistd.h>


#include <android/log.h>

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
        LOGE("%s%d", "父进程,native pid = ", getpid());
    } else {
        LOGE("%s%d", "子进程 native pid = ", getpid());
    }
    return pid;
}