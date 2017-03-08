package datastructure;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/8.
 * 2017年3月阿里巴巴实习生招聘在线编程题：
 * 对于一个长度为N的整形数组A，数组里所有的数都是正整数，对于两个满足0 <= X <= Y < N 的整数，A[X],A[X+1]...A[Y]构成A的一个切片，记作(X,Y)。
 * 用三个下标m1,m2,m3下标满足 0<m1 ,m1 + 1 < m2,m2 + 1 < m3 < N - 1。
 * 可以把这个整形数组分成(0,m1 - 1),(m1 + 1,m2),(m2 + 1,m3),(m3,N-1)四个切片。如果这个四个切片中的整数求和相等，称作“四等分”。
 * 编写一个函数，求一个给定的整形数组是否可以四等分，如果可以，返回一个布尔类型的true，如果不可以返回一个布尔类型的false。
 * 限制条件：数组A最多有1,000,000项，数组中整数的取值范围介于 -1,000,000到1,000,000之间。
 * 要求：函数计算的复杂度为O(n)，使用的额外存储空间(除了输入的数组之外)最多为O(n)。
 * 例子：
 * 对于数组A=[2,5,1,1,1,1,4,1,7,3,7]存在下标2,7,9使得数组分成4个分片[2,5],[1,1,1,4],[7],[7]，这3个分片内整数之和相等，所以对于这个数组，函数应该返回true
 * 对于数组A=[10,2,11,13,1,1,1,1,1]，找不到能把数组四等分的下标，所以函数应该返回false
 */
public class Alibaba_OnlineTest {
    static boolean resolve(int[] A) {
        HashMap<Integer, Integer> map = new HashMap();
        map.put(A[0], 0);
        for (int i = 1; i < A.length; i++) {
            A[i] += A[i - 1];
            if (map.containsKey(A[i])) {
                map.put(A[i], i);
            }
        }
        for (int i = 1; i < A.length - 1; i++) {
            if (tryI(i, A[i - 1], 3, A, map)) {
                return true;
            }
        }
        return false;
    }

    static boolean tryI(int i, int sum, int restNum, int[] A, HashMap<Integer, Integer> map) {
        if (i >= A.length - 1) {
            return false;
        }
        if (restNum == 1) {
            return A[A.length - 1] - A[i] == sum;
        }
        if (map.containsKey(sum + A[i])) {
            i = map.get(sum + A[i]) + 1;
            return tryI(i, sum, restNum - 1, A, map);
        }
        return false;
    }

    public static void main(String[] args) {
        int[] A = {2,5,1,1,1,1,4,1,7,3,7};
        int[] B = {10,2,11,13,1,1,1,1,1};
        int[] C = {1,2,1,13,4,1,1,1,1,1};
        int[] D = {1,2,1,-1,4,-1,1,1,1,1};
        System.out.println(resolve(A));
        System.out.println(resolve(B));
        System.out.println(resolve(C));
        System.out.println(resolve(D));
    }
}
