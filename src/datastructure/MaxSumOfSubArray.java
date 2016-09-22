package datastructure;

import java.util.ArrayList;

/**
 * Created by Lujunqiu on 2016/9/22.
 *一个有N个整数元素的一位数组(A[0], A[1],...,A[n-1], A[n])，这个数组当然有很多子数组，要求找出数组连续下标和的最大值
 * 测试用例：
 *      数组： {1, -2, 3, 5, -3, 2}   返回值为 8
        数组： {0, -2, 3, 5, -1, 2}   返回值为 9
        数组： {-9, -2, -3, -5, -6}   返回值为 -2
 */
public class MaxSumOfSubArray {
    public static void main(String[] args) {
        ArrayList<int []> test = new ArrayList<int[]>();
        test.add(new int[]{1,-2,3,5,-3,2});
        test.add(new int[]{0, -2, 3, 5, -1, 2});
        test.add(new int[]{-9, -2, -3, -5, -6});
        test.add(new int[]{-9, -2, -3, -5, 9});
        for (int[] a: test) {
            System.out.println(fun1(a));
        }
    }
    /**
     * 最大子数组和问题的普通解法，brute force穷举比较
     * 时间复杂度：O(n^2)
     */
    public static int fun1(int[] a){
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum = 0;
            for (int j = i; j < a.length; j++) {
                sum += a[j];
                if (max < sum){
                    max = sum;
                }
            }
        }
        return max;
    }

    public static int fun2(int[] a){

        return 1;
    }
}
