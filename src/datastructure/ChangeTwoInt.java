package datastructure;

/**
 * Created by Administrator on 2016/10/20.
 * 总结在不使用临时变量的情况下交换2个Integer整数的方法
 */
public class ChangeTwoInt {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        {
            a = a ^ b;
            b = b ^ a;
            a = a ^ b;
        }
        System.out.println("method(异或)" + a + "" + b);
        {
            a = a + b;
            b = a - b;
            a = a - b;
        }
        System.out.println("method(加法)" + a + "" + b);
        {
            a = b + 0 * (b = a);
        }
        System.out.println("method(赋值表达式内嵌)" + a + "" + b);
    }
}
