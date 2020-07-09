#include <jni.h>
#include <string>
#include <sys/mman.h>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_github_binderlearn_BinderMainActivity_stringFromJNI(JNIEnv *env, jobject /* this */) {
    string hello = "Hello from C++";


    FILE *pf = fopen("", 0);


    mmap(pf,4069,0,0,0,0);

    return env->NewStringUTF(hello.c_str());
}
