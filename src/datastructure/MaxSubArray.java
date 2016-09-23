package datastructure;

import java.util.ArrayList;

/**
 * Created by Lujunqiu on 2016/9/22.
 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
 *一个有N个整数元素的一位数组(A[0], A[1],...,A[n-1], A[n])，这个数组当然有很多子数组，要求找出数组连续下标和的最大值
 * 测试用例：
 *      数组： {1, -2, 3, 5, -3, 2}   返回值为 8
        数组： {0, -2, 3, 5, -1, 2}   返回值为 9
        数组： {-9, -2, -3, -5, -6}   返回值为 -2
        数组： {-2,1,-3,4,-1,2,1,-5,4}   返回值为 6
 */
public class MaxSubArray {
    public static void main(String[] args) {
        ArrayList<int []> test = new ArrayList<int[]>();
        test.add(new int[]{1,-2,3,5,-3,2});
        test.add(new int[]{0, -2, 3, 5, -1, 2});
        test.add(new int[]{-9, -2, -3, -5, -6});
        test.add(new int[]{-9, -2, -3, -5, 9});
        test.add(new int[]{-2,1,-3,4,-1,2,1,-5,4});
        for (int[] a: test) {
            System.out.println(fun1(a));
            System.out.println(fun2(a, 0, a.length - 1));
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

    /**
     * Divide and Conquer.
     *使用递归的方法求解：减小原问题的规模(分而治之，减而知之)最后使用递归基逐步返回结果
     *将数组2分为2个子数组，原问题的解可以理解为：左
     *时间复杂度：O(nlogn)
     */
    public static int fun2(int[] a,int left,int right) {
        if (left == right) {
            return a[left];
        }
        int mid = (left + right) / 2;
        int maxl = fun2(a, left, mid);
        int maxr = fun2(a, mid + 1, right);
        int maxc = fun2_Crossing_SubArray(a, left, mid, right);
        return Math.max(Math.max(maxl,maxr),maxc);
    }

    private static int fun2_Crossing_SubArray(int a[], int left, int mid, int right) {
        int leftSum = Integer.MIN_VALUE;
        int rightSum = Integer.MIN_VALUE;
        int temp = 0;
        for (int i = mid; i >=left ; i--) {
            temp += a[i];
            if (leftSum < temp) {
                leftSum = temp;
            }
        }
        temp = 0;
        for (int i = mid + 1; i <= right; i++) {
            temp += a[i];
            if (rightSum < temp) {
                rightSum = temp;
            }
        }
        return leftSum + rightSum;
    }
}
