//
// Created by admin on 2020/8/3.
//

#ifndef LEARNEXAMPLES_UTILS_H
#define LEARNEXAMPLES_UTILS_H

#include <jni.h>
#include <string>

using namespace std;

namespace util {
    string jstring2string(JNIEnv *env, jstring srcStr);

    jstring string2jstring(JNIEnv *env, const string &str);
}

#endif //LEARNEXAMPLES_UTILS_H
