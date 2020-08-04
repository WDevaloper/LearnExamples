#include <jni.h>
#include <string>
#include <sys/mman.h>
#include <fcntl.h>
#include <unistd.h>
#include <android/log.h>
#include <sys/stat.h>

#define LOGE(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"tag",FORMAT,##__VA_ARGS__);

using namespace std;

int m_fd;
int8_t *m_ptr;
int m_pagesize;
string filePath;


int DEFAULT_POSITION = 4;
int m_position = DEFAULT_POSITION;


size_t readActualSize() {
    uint32_t actualSize = 0;
    memcpy(&actualSize, m_ptr, 4);
    return actualSize;
}


static bool getFileSize(int fd, size_t &size) {
    struct stat st = {};
    if (fstat(fd, &st) != -1) {
        size = (size_t) st.st_size;
        return true;
    }
    return false;
}

size_t getPageSize() {
    return static_cast<size_t>(getpagesize());
}



//物理地址
//虚拟地址  不能访问物理地址

// 内核空间和物理空间一一对应的。不能改变，也不能访问其他地址空间
// 但是可以通过高端内存去访问其他内存(128m)（alloc_page() 也是映射），可以指向物理地址任何地方


// 内核空间是可以直接操作物理设备的
// 用户空间是不能直接读取文件的

extern "C"
JNIEXPORT void JNICALL
Java_com_github_binderlearn_BinderMainActivity_init(JNIEnv *env, jobject thiz, jstring path) {
    filePath = env->GetStringUTFChars(path, NULL);

    m_fd = open(filePath.c_str(), O_RDWR | O_CREAT, S_IRWXU);
    //获取到一个文件最小单元
    m_pagesize = getpagesize();
    // 文件设置成 4096k 和内存一样   也就是一页
    ftruncate(m_fd, m_pagesize);

    //重新定位文件指针
    //SEEK_END文件末尾，0偏移量
    long filesize = lseek(m_fd, 0, SEEK_END);
    LOGE("%d", filesize)
    //返回当前的文件指针，相对于文件开头的位移量
    LOGE("%s%d", "m_pagesize", m_pagesize)

    // 映射 用户空间和File之间的映射 不需要拷贝
    m_ptr = static_cast<int8_t *>(mmap(NULL,
                                       m_pagesize,//binder 128k   4k正数倍
                                       PROT_READ | PROT_WRITE,
                                       MAP_SHARED,//共享
                                       m_fd,//file
                                       0));


    //0+4=4  0000mmap
    // 0+ 8 = 8
    //读取写入位置
    if (m_position <= DEFAULT_POSITION) {
        size_t actualSize = readActualSize();
        if (actualSize > 0) {
            m_position = actualSize;
        } else {
            m_position += actualSize;
        }
        LOGE("%s%d", "m_position", m_position)
    }

}

extern "C"
JNIEXPORT void JNICALL
Java_com_github_binderlearn_BinderMainActivity_write(JNIEnv *env, jobject thiz, jstring data) {
    // 写入文件
    //write() 不需要  我们需要操作内存  会直接响应到文件中

    // 获取文件大小
    struct stat st = {};
    size_t m_segmentSize = 0;
    if (fstat(m_fd, &st) != -1) {
        m_segmentSize = static_cast<size_t>(st.st_size); // 获取文件大小
        LOGE("%d", m_segmentSize)
    }

    // ioctl()

    // 3. 验证文件的大小是否小于一个内存页, 一般为 4kb
    if (m_segmentSize < m_pagesize) {
        m_segmentSize = static_cast<size_t> (m_pagesize);
        // 3.1 通过 ftruncate 将文件大小对其到内存页
        // 3.2 通过 zeroFillFile 将文件对其后的空白部分用 0 填充
        // 拓展文件
        ftruncate(m_fd, m_segmentSize);
    }


    // write
    string content = env->GetStringUTFChars(data, nullptr);
    size_t numberOfBytes = content.size();
    memcpy(m_ptr + m_position, content.data(), numberOfBytes); //直接把字符串copy到内存(m_ptr)
    m_position += numberOfBytes;//我觉得我们应该要保存dataPosition 记录该从哪个地方写入数据

    //写入位置放在前面4(int)
    memcpy(m_ptr, &m_position, 4);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_github_binderlearn_BinderMainActivity_read(JNIEnv *env, jobject thiz) {
    jstring data;
    memcpy(&data, m_ptr + 4, m_pagesize);
    return data;
}