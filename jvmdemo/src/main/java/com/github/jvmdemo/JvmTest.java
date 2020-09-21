package com.github.jvmdemo;


// 查看字节码文件
// 使用 javap -p -v JvmTest.class >> JvmTest.txt   JDK自带工具
// 使用 jclasslib  可视化工具
public class JvmTest {
    //常量
    final String Fs = "常在河边走，哪有不湿鞋";
    //静态变量
    static String Ss = "以静制动";
    //次数
    int count = 0;

    //King老师出差
    public  void king(int money) {
        //13号妓师
        Object tech13 = new Object();
        //调用一次13号服务
        tech13.hashCode();
        int i;
        money = money - 100; //花费100

        count++;


        //if(count ==2000) return;
        king(money);
    }

    public  void test(int ml) {
        System.out.println("test>>>>>>>>>>>>>>>>");
    }

    public static void main(String[] args) throws Throwable {
        JvmTest javaStack = new JvmTest();
        try {
            javaStack.king(10000);
        } catch (Throwable e) {
            //输出异常时循环的次数
            System.out.println("栈异常！调用方法(king)的次数()：" + javaStack.count);
            throw e;
        }
    }


    public static void testStatic(int ImStatic) {
        System.out.println("测试静态方法是否有【this】");
    }
}
