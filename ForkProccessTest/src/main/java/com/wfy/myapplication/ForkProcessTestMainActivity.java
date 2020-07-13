package com.wfy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import java.util.List;


/**
 * fork:
 * 操作系统会复制一个与父进程完全相同的子进程，虽说是父子关系，但是在操作系统看来，他们更像兄弟关系，
 * 这2个进程共享代码空间，但是数据空间是互相独立的,子进程数据空间中的内容是父进程的完整拷贝，
 * 指令指针也完全相同，子进程拥有父进程当前运行到的位置.
 * <p>
 * <p>
 * <p>
 * 所以Android 系统中的进程是由init.rc脚本创建Zygote（第一个进程），所以不能是哪个进程都是Zygote进程通过fork出来的
 * <p>
 * <p>
 * <p>
 * 主进程java pid: 3494
 * 当前进程ID= 3494
 * 当前进程的父进程ID= 1472
 * 父进程, pid = 3494
 * 结束了3494
 * 子进程 pid = 3512
 * 结束了3512
 */
public class ForkProcessTestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fork_proccess_test_main);

        Log.e("tag", "主进程java pid: " + Process.myPid());
        //子进程拥有父进程当前运行到的位置
        int forkId = fork();
        //让线程先不结束
        Log.e("tag", forkId + ",结束了," + Process.myPid());

        String processName = getProcessName(ForkProcessTestMainActivity.this);
        Log.e("tag", "processName= " + processName);
    }

    static {
        System.loadLibrary("native-lib");
    }

    public native int fork();

    public static String getProcessName(Context cxt) {
        int pid = Process.myPid();

        Log.e("tag",">>>>>>>>>"+pid);
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            Log.e("tag",">>>>>>>>>"+runningApps);
            return null;
        }

        Log.e("tag",">>>>>>>>>"+runningApps);
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        Log.e("tag",">>>>>>>>>"+runningApps);

        return null;
    }

}
