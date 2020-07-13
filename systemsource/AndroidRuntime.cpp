void AndroidRuntime::start(const char *className, const Vector <String8> &options, bool zygote) {

    ///...............

    /// 开启Java虚拟机
    if (startVm(&mJavaVM, &env, zygote, primary_zygote) != 0) {
        return;
    }

    ///...............

    /// classNameStr是传入的参数className转化而来，值为com.android.internal.os.ZygoteInit
    classNameStr = env->NewStringUTF(className);
    env->SetObjectArrayElement(strArray, 0, classNameStr);

    ///..........

    /// 加载com/android/internal/os/ZygoteInit类，这是jni反射
    jclass startClass = env->FindClass(slashClassName);

    if (startClass == NULL) {
        ALOGE("JavaVM unable to locate class '%s'\n", slashClassName);
    } else {
        // 通过反射拿到com/android/internal/os/ZygoteInit的main函数
        jmethodID startMeth = env->GetStaticMethodID(startClass, "main",
                                                     "([Ljava/lang/String;)V");
        if (startMeth == NULL) {
            ///.....
        } else {
            /// 反射调用com/android/internal/os/ZygoteInit的main函数， 从Native层进入了Java层
            env->CallStaticVoidMethod(startClass, startMeth, strArray);
        }
    }
}