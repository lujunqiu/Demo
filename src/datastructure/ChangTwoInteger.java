package datastructure;

import java.lang.reflect.Field;

/**
 * Created by Lujunqiu on 2016/11/1.
 * 给定两个(基本数据类型的包装类)Integer类型的变量a和b，要求实现一个函数swap，交换他们的值。
 * 分析问题:根据给出的swap函数接口，函数返回值是void说明我们需要在函数体内部完成Integer对象的内部值得交换。由于我们给函数传递的是2个引用的值（Integer在jvm看作是类）
 * 如果我们简单的交换引用变量a，b的值（对象实例的内存映像）
    tmp = i1;    // tmp得到i1的值：0x1234（对象实例的内存地址）
    i1    = i2;     // i1得到i2的值：0x1265
    i2    = tmp; // i2得到tmp的值：0x1234
 * 最终，a和b还是指向对应的内存区域，而这个内存区域的值还是不变。所以，在函数外部是无法体现交换的结果的，swap这个函数等于啥都没干。
 * 通过查看Integer源码实现，实际内部将整数值存放在一个叫int类型的value变量里。他虽然有get函数，但是却没有set函数。因为他是final的（不可修改）！

 *  以上分析之后，使用常规的方法貌似已经无法解决我们的问题了，但是可以利用java的反射来帮助我们求解。
 */
public class ChangTwoInteger {
    public static void  main(String[] args) {
        Integer a = 1, b = 2 ;
        System.out.println("before swap a = " + a + "b = " + b);
        swap(a , b);
        System.out.println("after swap a = " + a + "b = " + b);
    }
    public static void swap(Integer i1 , Integer i2) {
        try {
            Field f = Integer.class.getDeclaredField("value");//getDeclaredField()方法，暴力反射，是获得类上的字段，无论是公有还是私有的
            f.setAccessible(true);//由于Integer内部保存int数值的变量value是private 和 final的，需要修改访问控制权限。
                                 // 传入的参数的值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。


            int temp = i1.intValue();
            f.setInt(i1, i2.intValue());
            f.setInt(i2,temp);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
