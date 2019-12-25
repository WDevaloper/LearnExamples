package com.gavin.plugin.lifecycle;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @Describe:
 */
public class MyAnnotationVisitor extends AnnotationVisitor {
    private static WeakHashMap<String, List<String>> annData = new WeakHashMap<>();

    public MyAnnotationVisitor(AnnotationVisitor av) {
        super(Opcodes.ASM6, av);
    }

    @Override
    public void visit(String name, Object value) {
        System.out.println(name + " =====MyAnnotationVisitor Class  visit start");
        if (name != null && name.equals("time")) {
            LogHelper.time = (long) value;
        }
        if (name != null && name.equals("tag")) {
            LogHelper.tag = (String) value;
        }

        System.out.println(name + "===== MyAnnotationVisitor Class visitAnnotation visit: " + value);
        super.visit(name, value);
    }


    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        System.out.println(name + "===== MyAnnotationVisitor Class visitAnnotation: " + desc);
        return super.visitAnnotation(name, desc);
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        System.out.println(name + " =====MyAnnotationVisitor Class visitAnnotation visit: " + value + "  desc " + desc);
        super.visitEnum(name, desc, value);
    }


    /**
     * av0 = mv.visitAnnotation("Lcom/gavin/asmdemo/Test;", true);
     * av0.visit("tag", "test");
     * {
     * AnnotationVisitor av1 = av0.visitArray("targetClass");
     * av1.visit(null, Type.getType("Lcom/gavin/asmdemo/ICheckStatusImpl;"));
     * av1.visit(null, Type.getType("Lcom/gavin/asmdemo/SecondActivity;"));
     * av1.visitEnd();
     * }
     * av0.visitEnd();
     *
     * @param name
     * @return
     */
    @Override
    public AnnotationVisitor visitArray(String name) {
        System.out.println(name + " =====MyAnnotationVisitor Class visitArray visit");
        annData.put(name, new ArrayList<>());
        return new MyAnnotationVisitor(super.visitArray(name));
    }

    @Override
    public void visitEnd() {
        System.out.println(" =====MyAnnotationVisitor Class visitArray visitEnd");
        super.visitEnd();
    }
}
