##### 占位式插件化

#### 什么是占位式插件化？
1、就是在宿主定义标准，让插件实现该标准，然后宿主通过标准注入宿主的上下文环境给插件提供上下文；
2、实际上占位式插件化的原理就是，在宿主中提供一个代理的Activity，然后通过加载插件的资源，并在宿主中展示出来；
3、占位式插装就是宿主通过ProxyActivity去加载插件包(apk)中的资源并将资源展示到ProxyActivity

##### 开始之前大概了解一下DexClassLoader：
1、Bootstrap ClassLoader：负责加载<JAVA_HOME>\lib目录下或者被-Xbootclasspath参数所指定的路径的，并且是被虚拟机所识别的库到内存中。
2、扩展类加载器（Extension ClassLoader）：负责加载<JAVA_HOME>\lib\ext目录下或者被java.ext.dirs系统变量所指定的路径的所有类库到内存中。
3、应用类加载器（Application ClassLoader）：负责加载用户类路径上的指定类库，如果应用程序中没有实现自己的类加载器，一般就是这个类加载器去加载应用程序中的类库。


#### 双亲委派模型的整个工作流程非常的简单，如下所示：
如果一个类加载器收到了加载类的请求，它不会自己立即去加载类，它会先去请求父类加载器，每个层次的类加载器都是如此。
层层传递，直到传递到最高层的类加载器，只有当 父类加载器反馈自己无法加载这个类，才会有当前子类加载器去加载该类。

Java虚拟机加载的是class文件，而Android虚拟机加载的是dex文件（多个class文件合并而成），所以两者既有相似的地方，也有所不同。

1、PathClassLoader: 主要用于系统和app的类加载器,其中optimizedDirectory为null, 采用默认目录/data/dalvik-cache/
2、DexClassLoader: 可以从包含classes.dex的jar或者apk中，加载类的类加载器, 可用于执行动态加载, 但必须是app私有可写目录来缓存odex文件.
 能够加载系统没有安装的apk或者jar文件，因此很多插件化方案都是采用DexClassLoader;



宿主（运行app）

标准

实现宿主提供的标准

插件包（不能运行的app），即没有任何运行环境，包括context
插件的特点：插件是没有组件环境的



1、定制标准；
2、加载插件（Activity class，layout）