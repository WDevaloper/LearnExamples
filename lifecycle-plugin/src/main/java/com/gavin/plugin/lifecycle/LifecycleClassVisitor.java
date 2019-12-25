package com.gavin.plugin.lifecycle;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.function.Consumer;


// ClassAdapter

/**
 * 删除类的字段、方法、指令：只需在职责链传递过程中中断委派，不访问相应的 visit 方法即可，
 * 比如删除方法时只需直接返回 null，而不是返回由 visitMethod方法返回的 MethodVisitor对象。
 * <p>
 * <p>
 * 修改类、字段、方法的名字或修饰符：在职责链传递过程中替换调用参数。
 * <p>
 * <p>
 * ASM 的最终的目的是生成可以被正常装载的 class 文件，
 * 因此其框架结构为客户提供了一个生成字节码的工具类 —— ClassWriter。
 * 它实现了 ClassVisitor接口，而且含有一个 toByteArray()函数，返回生成的字节码的字节流，
 * 将字节流写回文件即可生产调整后的 class 文件。
 * 一般它都作为职责链的终点，把所有 visit 事件的先后调用（时间上的先后），
 * 最终转换成字节码的位置的调整（空间上的前后）
 * <p>
 * <p>
 * <p>
 * 暴露接口，
 */
public class LifecycleClassVisitor extends ClassVisitor implements Opcodes {

    public LifecycleClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("LifecycleClassVisitor : visit -----> started: " + name);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    //访问类的方法
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new MyMethodVisitor(mv, access, name, desc);
    }


    //访问class的成员
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        FieldVisitor fv = cv.visitField(access, name, desc, signature, value);
        return new MyFieldVisitor(fv);
    }

    //访问类的注解
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println("LifecycleClassVisitor : visit -----> visitAnnotation: " + desc);
        return super.visitAnnotation(desc, visible);
    }
}



