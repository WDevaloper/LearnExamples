package com.github.apm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

public class APMMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_p_m_main);
        try {
            ClassPool pool = new ClassPool();
            // 1. 创建一个空类
            CtClass cc = pool.makeClass("com.github.apm.Person");

            // 2. 新增一个字段 private String name;
            // 字段名为name
            CtField param = new CtField(pool.get("java.lang.String"), "name", cc);
            // 访问级别是 private
            param.setModifiers(Modifier.PRIVATE);
            // 初始值是 "xiaoming"
            cc.addField(param, CtField.Initializer.constant("xiaoming"));

            // 3. 生成 getter、setter 方法
            cc.addMethod(CtNewMethod.setter("setName", param));
            cc.addMethod(CtNewMethod.getter("getName", param));

            // 4. 添加无参的构造函数
            CtConstructor cons = new CtConstructor(new CtClass[]{}, cc);
            cons.setBody("{name = \"xiaohong\";}");
            cc.addConstructor(cons);

            // 5. 添加有参的构造函数
            cons = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, cc);
            // $0=this / $1,$2,$3... 代表方法参数
            cons.setBody("{$0.name = $1;}");
            cc.addConstructor(cons);

            // 6. 创建一个名为printName方法，无参数，无返回值，输出name值
            CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, cc);
            ctMethod.setModifiers(Modifier.PUBLIC);
            ctMethod.setBody("{System.out.println(name);}");
            cc.addMethod(ctMethod);
            //这里会将这个创建的类对象编译为.class文件
            cc.writeFile("E:/developer/project/LearnExamples/skin_core/src/main/java/");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
