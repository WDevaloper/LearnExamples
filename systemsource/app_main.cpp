int main(int argc, char *const argv[]) {

    //argc = argument 可选参数  -Xzygote /system/bin --zygote --start-system-server --socket-name=zygote
    // argv = option：当前服务设置的选项 class main
    // argv[0] =  /system/bin
    AppRuntime runtime(argv[0], computeArgBlockSize(argc, argv));
    // Process command line arguments 处理命令行参数
    // ignore argv[0] 忽略  argv[0]
    argc--;
    argv++;

    // Everything up to '--' or first non '-' arg goes to the vm.
    //
    // The first argument after the VM args is the "parent dir", which
    // is currently unused.
    //
    // After the parent dir, we expect one or more the following internal
    // arguments :
    //
    // --zygote : Start in zygote mode  启动zygote的模式
    // --start-system-server : Start the system server. 启动系统服务器。
    // --application : Start in application (stand alone, non zygote) mode.在应用程序（独立，非zygote）模式下启动
    // --nice-name : The nice name for this process.这个进程的名字。
    //


    // 对于非zygote启动，这些参数后跟主类名称。所有剩余的参数都传递到该类的main方法。
    // For non zygote starts, these arguments will be followed by
    // the main class name. All remaining arguments are passed to
    // the main method of this class.
    //


    // 对于zygote启动，所有剩余的参数都传递给合子。主函数。
    // For zygote starts, all remaining arguments are passed to the zygote.
    // main function.
    //
    // Note that we must copy argument string values since we will rewrite the
    // entire argument block when we apply the nice name to argv0.
    //
    // As an exception to the above rule, anything in "spaced commands"
    // goes to the vm even though it has a space in it.



    ///...................................


    // 解析运行时参数。停在第一个无法识别的选项。
    // Parse runtime arguments.  Stop at first unrecognized option.
    bool zygote = false;
    bool startSystemServer = false;
    bool application = false;
    String8 niceName;
    String8 className;

    ++i;  // Skip unused "parent dir" argument.
    while (i < argc) {
        const char *arg = argv[i++];
        if (strcmp(arg, "--zygote") == 0) {// 是否是zygote模式启动进程，如果
            zygote = true;
            niceName = ZYGOTE_NICE_NAME;//进程名 = zygote
        } else if (strcmp(arg, "--start-system-server") == 0) {//是否启动system-service
            startSystemServer = true;
        } else if (strcmp(arg, "--application") == 0) {// 是否是启动application
            application = true;
        } else if (strncmp(arg, "--nice-name=", 12) == 0) {//进程名
            niceName.setTo(arg + 12);
        } else if (strncmp(arg, "--", 2) != 0) {
            className.setTo(arg);
            break;
        } else {
            --i;
            break;
        }
    }

    ///.........................

    if (zygote) {// 调用AppRuntime的start方法并把com.android.internal.os.ZygoteIni.java全类名当做参数传进去
        runtime.start("com.android.internal.os.ZygoteInit", args, zygote);
    } else if (className) {// 传的是应用的类名
        runtime.start("com.android.internal.os.RuntimeInit", args, zygote);
    } else {
        fprintf(stderr, "Error: no class name or --zygote supplied.\n");
        app_usage();
        LOG_ALWAYS_FATAL("app_process: no class name or --zygote supplied.");
    }
}
