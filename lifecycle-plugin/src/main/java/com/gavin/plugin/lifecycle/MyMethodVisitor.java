package com.gavin.plugin.lifecycle;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;


public class MyMethodVisitor extends AdviceAdapter {

    private String name;
    private String desc;

    protected MyMethodVisitor(MethodVisitor methodVisitor, int i1, String name, String desc) {
        super(Opcodes.ASM6, methodVisitor, i1, name, desc);
        this.name = name;
        this.desc = desc;
    }

    /**
     * 访问方法的注解
     * <p>
     * 一般会先访问方法的注解，然后can访问方法
     * <p>
     * 需要通过注解的方式加字节码才会重写这个方法来进行条件过滤
     *
     * @param desc
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println(visible + " MyMethodVisitor method visitAnnotation: " + desc);
        AnnotationVisitor av = mv.visitAnnotation(desc, visible);
        return new MyAnnotationVisitor(av);
    }

    /**
     * 每个开始visitor方法都会先执行这个方法:
     * visitAnnotation -> visitCode -> onMethodEnter -> onMethodExit -> visitEnd
     */
    @Override
    public void visitCode() {
        System.out.println(" MyMethodVisitor Class visitCode");
        super.visitCode();
    }

    /**
     * 每个结束visitor方法都会先执行这个方法
     * onMethodExit -> visitEnd
     */
    @Override
    public void visitEnd() {
        System.out.println(" MyMethodVisitor Class visitEnd");
        super.visitEnd();
    }


    //在方法前面执行
    @Override
    protected void onMethodEnter() {
        System.out.println(desc + "  MyMethodVisitor Class " + name + " Method Enter: ");
        //方法执行前插入
        mv.visitLdcInsn(LogHelper.tag);
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("-------> onCreate : ");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }

    //在方法后面执行
    @Override
    protected void onMethodExit(int i) {
        System.out.println(desc + "  MyMethodVisitor Class For " + name + " Method Exit");
        mv.visitLdcInsn("TAG");
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("-------> onCreate aaa: ");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }

}
