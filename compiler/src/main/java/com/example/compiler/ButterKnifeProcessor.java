package com.example.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)
// 允许/支持的注解类型，让注解处理器处理（新增annotation module）
@SupportedAnnotationTypes({Constants.BIND_TYPE, Constants.CLICK_TYPE})
// 指定JDK编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
// 注解处理器接收的参数
//@SupportedOptions("content")
public class ButterKnifeProcessor extends AbstractProcessor {
    // 操作Element工具类 (类、函数、属性都是Element)
    private Elements elementUtils;

    // type(类信息)工具类，包含用于操作TypeMirror的工具方法
    private Types typeUtils;

    // Messager用来报告错误，警告和其他提示信息
    private Messager messager;


    private Map<TypeElement, List<VariableElement>> tempFields = new HashMap<>();
    private Map<TypeElement, List<ExecutableElement>> tempMethods = new HashMap<>();

    // 文件生成器 类/资源，Filter用来创建新的源文件，class文件以及辅助文件
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        TypeSpec clazz = clazz(builtinTypeField(),          // int
                arrayTypeField(),            // int[]
                refTypeField(),              // File
                typeField(),                 // T
                parameterizedTypeField(),    // List<String>
                wildcardTypeField(),         // List<? extends String>
                constructor(),               // 构造函数
                method(code()));             // 普通方法
        try {
            JavaFile javaFile = JavaFile.builder("com.example.apt_ioc", clazz).build();
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * `public abstract class Clazz<T> extends String implements Serializable, Comparable<String>, Comparable<? extends String> {
     * ...
     * }`
     *
     * @return
     */
    public static TypeSpec clazz(FieldSpec builtinTypeField,
                                 FieldSpec arrayTypeField,
                                 FieldSpec refTypeField,
                                 FieldSpec typeField,
                                 FieldSpec parameterizedTypeField,
                                 FieldSpec wildcardTypeField,
                                 MethodSpec constructor,
                                 MethodSpec methodSpec) {
        return TypeSpec.classBuilder("Clazz")
                // 限定符
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                // 泛型
                .addTypeVariable(TypeVariableName.get("T"))

                // 继承与接口
                .superclass(String.class)
                .addSuperinterface(Serializable.class)
                .addSuperinterface(ParameterizedTypeName.get(Comparable.class, String.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Map.class),
                        TypeVariableName.get("T"),
                        WildcardTypeName.subtypeOf(String.class)))

                // 初始化块
                .addStaticBlock(CodeBlock.builder().build())
                .addInitializerBlock(CodeBlock.builder().build())

                // 属性
                .addField(builtinTypeField)
                .addField(arrayTypeField)
                .addField(refTypeField)
                .addField(typeField)
                .addField(parameterizedTypeField)
                .addField(wildcardTypeField)

                // 方法 （构造函数也在此定义）
                .addMethod(constructor)
                .addMethod(methodSpec)

                // 内部类
                .addType(TypeSpec.classBuilder("InnerClass").build())

                .build();
    }

    /**
     * 内置类型
     */
    public static FieldSpec builtinTypeField() {
        // private int mInt;
        return FieldSpec.builder(int.class, "mInt", Modifier.PRIVATE).build();
    }

    /**
     * 数组类型
     */
    public static FieldSpec arrayTypeField() {
        // private int[] mArr;
        return FieldSpec.builder(int[].class, "mArr", Modifier.PRIVATE).build();
    }

    /**
     * 需要导入 import 的类型
     */
    public static FieldSpec refTypeField() {
        // private File mRef;
        return FieldSpec.builder(File.class, "mRef", Modifier.PRIVATE).build();
    }

    /**
     * 泛型
     */
    public static FieldSpec typeField() {
        // private File mT;
        return FieldSpec.builder(TypeVariableName.get("T"), "mT", Modifier.PRIVATE).build();
    }

    /**
     * 参数化类型
     */
    public static FieldSpec parameterizedTypeField() {
        // private List<String> mParameterizedField;
        return FieldSpec.builder(ParameterizedTypeName.get(List.class, String.class),
                "mParameterizedField",
                Modifier.PRIVATE)
                .build();
    }

    /**
     * 通配符参数化类型
     *
     * @return
     */
    public static FieldSpec wildcardTypeField() {
        // private List<? extends String> mWildcardField;
        return FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class),
                WildcardTypeName.subtypeOf(String.class)),
                "mWildcardField",
                Modifier.PRIVATE)
                .build();
    }

    /**
     * 构造函数
     */
    public static MethodSpec constructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .build();
    }

    /**
     * `@Override
     * public <T> Integer method(String string, T t, Map<Integer, ? extends T> map) throws IOException, RuntimeException {
     * ...
     * }`
     *
     * @param codeBlock
     * @return
     */
    public static MethodSpec method(CodeBlock codeBlock) {
        return MethodSpec.methodBuilder("method")
                .addAnnotation(Override.class)
                .addTypeVariable(TypeVariableName.get("T"))
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addParameter(String.class, "string")
                .addParameter(TypeVariableName.get("T"), "t")
                .addParameter(ParameterizedTypeName.get(ClassName.get(Map.class),
                        ClassName.get(Integer.class),
                        WildcardTypeName.subtypeOf(TypeVariableName.get("T"))),
                        "map")
                .addException(IOException.class)
                .addException(RuntimeException.class)
                .addCode(codeBlock)
                .build();
    }

    /**
     * ‘method’ 方法中的具体语句
     */
    public static CodeBlock code() {
        return CodeBlock.builder()
                .addStatement("int foo = 1")
                .addStatement("$T bar = $S", String.class, "a string")

                // Object obj = new HashMap<Integer, ? extends T>(5);
                .addStatement("$T obj = new $T(5)",
                        Object.class, ParameterizedTypeName.get(ClassName.get(HashMap.class),
                                ClassName.get(Integer.class),
                                WildcardTypeName.subtypeOf(TypeVariableName.get("T"))))

                // method(new Runnable(String param) {
                //   @Override
                //   void run() {
                //   }
                // });
                .addStatement("baz($L)", TypeSpec.anonymousClassBuilder("$T param", String.class)
                        .superclass(Runnable.class)
                        .addMethod(MethodSpec.methodBuilder("run")
                                .addAnnotation(Override.class)
                                .returns(TypeName.VOID)
                                .build())
                        .build())

                // for
                .beginControlFlow("for (int i = 0; i < 5; i++)")
                .endControlFlow()

                // while
                .beginControlFlow("while (false)")
                .endControlFlow()

                // do... while
                .beginControlFlow("do")
                .endControlFlow("while (false)")

                // if... else if... else...
                .beginControlFlow("if (false)")
                .nextControlFlow("else if (false)")
                .nextControlFlow("else")
                .endControlFlow()

                // try... catch... finally
                .beginControlFlow("try")
                .nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("e.printStackTrace()")
                .nextControlFlow("finally")
                .endControlFlow()

                .addStatement("return 0")
                .build();
    }

}
