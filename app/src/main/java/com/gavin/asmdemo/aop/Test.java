package com.gavin.asmdemo.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Retention(RetentionPolicy.SOURCE): ASM 在字节码中找不到的
 * @Retention(RetentionPolicy.CLASS): ASM 在字节码中找地到，@Lcom/gavin/asmdemo/Test;() // invisible
 * @Retention(RetentionPolicy.RUNTIME) ASM 在字节码中找地到，@Lcom/gavin/asmdemo/Test;()
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    long time() default 300L;

    int test() default 10;

    String tag() default "TAG";


    String value() default "test2";

    Class<?>[] targetClass();

}
