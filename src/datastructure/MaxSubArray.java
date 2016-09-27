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
            System.out.println(fun1(a));//普通解法：O(n^2)
            System.out.println(fun2(a, 0, a.length - 1));//递归解法：O(nlogn)
            System.out.println(fun3(a));//动态规划求解：O(n)
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
     *将数组2分为2个子数组，原问题的解可以二分为左后2个子数组，用同样的方法求解左右2个子数组的最大子数组和，最后递归基是分解到子数组只有一个元素就返回该数值就可以。
     *但是我们要考虑到一种特殊情况就是最大子数组的解穿过2个子数组，左数组和右数组的一部分共同构成最大子数组，这种情况与递归求解的问题形式不一样，我们可以用其他方法求解。
     *时间复杂度：O(nlogn)
     */
    public static int fun2(int[] a,int left,int right) {
        //递归基
        if (left == right) {
            return a[left];
        }
        int mid = (left + right) / 2;
        int maxl = fun2(a, left, mid);//求解左子数组的解
        int maxr = fun2(a, mid + 1, right);//求解右子数组的解
        int maxc = fun2_Crossing_SubArray(a, left, mid, right);//求解穿过中心的最大子数组的和
        return Math.max(Math.max(maxl,maxr),maxc);//在上面3个数值中选择较大值输出即可
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

    /**
     *动态规划求解：动态规划求解问题的特点当前状态是历史的完全总结，过程的演变不再受此前各种状态及决策的影响。
     *我们假设已经得知了假设已经知道(A[0], ...,A[n-1])中和最大的一段数组之和为All，那么 (A[0], ..., A[n])问题的解可以看成是：max{A[n]，All，A[n] + Start[n-1]};Start[n-1]是(A[0],...,A[n-1])中包含A[n-1]的和最大的一段数组。
     *Start[n]是(A[0],...,A[n])中包含A[n]的和最大的一段数组求解方法：假设我们已知Start[n-1]那么Start[n]=max{Start[n-1]+A[n] ,A[n]};
     *从上面的分析可知，我们从头开始遍历一次数组记录相关数据即可。
     *时间复杂度：O(n)
     */
    public static int fun3(int[] a){
        int tempSum = a[0];
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            tempSum = Math.max(tempSum + a[i], a[i]);
            max = Math.max(max, tempSum);
        }
        return max;
    }
}
