# 自定义控件

# Android 事件分发

# 屏幕适配、沉浸式

# AOP Android函数插桩（Gradle Transform + ASM）

## 函数插桩

### 是什么函数插桩
**插桩**：目标程序代码中某些位置**插入或修改**成一些代码，从而在目标程序运行过程中获取某些程序状态并加以分析。简单来说就是**在代码中插入代码**。
那么**函数插桩**，便是在函数中插入或修改代码，在Android编译过程中，往字节码里插入自定义的字节码，所以也可以称为**字节码插桩**。

### 作用
* 函数插桩可以帮助我们实现很多手术刀式的代码设计，如**无埋点统计上报、轻量级AOP**等。
* 应用到在**Android**中，可以用来做用行为统计、方法耗时统计等功能。
* 函数插桩可以实现轻量级的AOP，比如在指定注解标志处，判断网络状态或者登陆状态。
