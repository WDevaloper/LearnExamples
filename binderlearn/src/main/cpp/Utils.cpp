#include "Utils.h"
#include <jni.h>
#include <string>


string util::jstring2string(JNIEnv *env, jstring srcStr) {
    if (srcStr) {
        const char *str = env->GetStringUTFChars(srcStr, nullptr);
        if (str) {
            string result(str);
            env->ReleaseStringUTFChars(srcStr, str);
            return result;
        }
    }
    return "";
}


jstring util::string2jstring(JNIEnv *env, const string &str) {
    return env->NewStringUTF(str.c_str());
}