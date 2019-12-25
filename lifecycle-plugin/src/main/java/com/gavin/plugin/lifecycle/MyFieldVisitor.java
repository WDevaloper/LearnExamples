package com.gavin.plugin.lifecycle;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class MyFieldVisitor extends FieldVisitor {
    public MyFieldVisitor(FieldVisitor fv) {
        super(Opcodes.ASM6, fv);
    }


    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println(visible + " FieldVisitor Class visitAnnotation: " + desc);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attr) {
        System.out.println(attr.type + " FieldVisitor Class visitAttribute: " + attr.isCodeAttribute());
        super.visitAttribute(attr);
    }

    @Override
    public void visitEnd() {
        System.out.println(" FieldVisitor Class visitEnd");
        super.visitEnd();
    }
}
