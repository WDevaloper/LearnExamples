package com.github.jvmdemo;

import java.io.Serializable;

//类和接口的全限定名(Fully Qualified Name)
//字段的名称和描述符号(Descriptor)
//方法的名称和描述符

//为了规避泛型的类型擦除，在运行时无法 获取泛型信息，那么在字节码加入了Signature，所以通过反射是可以获取到泛型信息


//类信息都保存在常量池中，在JVM运行时通过解析，将常量池中的字符引用翻译为直接引用，
// 即当JVM运行时，需要从常量池获得对应的符号引用，再在类创建或运行时解析并翻译到具体的内存地址中。

//反射基本上是一种解析操作:
// 解析阶段是虚拟机将常量池内的符号引用替换为直接引用的过程，
// 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型、方法句柄和调用点限定符7类符号引用进行。
// 符号引用就是一组符号来描述目标，可以是任何字面量。
// 所以反射就是将符号引用解析为直接引用，
// 然后通过直接引用操作类的属性和方法的，比如：通过反射拿到一个对象的成员方法（即方法的直接引用）
// 那么需要调用此方法，也是通过直接引用操作方法对应的内存。
public class GenericTypeErasure<T> {

    private String filed1;
    private Foo filed2;
    private String filed3;

    public void show(T data) {
        System.out.println("data = " + data);
    }


    public void setData(T data) {
        System.out.println("data = " + data);
    }


    public <E> void getData(E e) {
        System.out.println("e = " + e);
    }


    public <E extends String> void getE(E e) {
        System.out.println("getE");
    }

    public <D extends Serializable> void getD(D d) {
        System.out.println("getD");
    }
}
