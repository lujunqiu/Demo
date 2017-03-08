package datastructure;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/8.
 * 2017年3月阿里巴巴实习生招聘在线编程题：
 * 对于一个长度为 N 的整型数组A，数组里所有的数都是正整数，对于两个满足0 <= X <= Y < N 的整数，A[X],A[X+1]...A[Y]构成A的一个切片，记作(X,Y)。
 * 用三个下标m1,m2,m3下标满足 0<m1 ,m1 + 1 < m2,m2 + 1 < m3 < N - 1。
 * 可以把这个整型数组分成(0,m1 - 1),(m1 + 1,m2),(m2 + 1,m3),(m3,N-1)四个切片。如果这个四个切片中的整数求和相等，称作“四等分”。
 * 编写一个函数，求一个给定的整形数组是否可以四等分，如果可以，返回一个布尔类型的true，如果不可以返回一个布尔类型的false。
 * 限制条件：数组A最多有1,000,000项，数组中整数的取值范围介于 -1,000,000到1,000,000之间。
 * 要求：函数计算的复杂度为O(n)，使用的额外存储空间(除了输入的数组之外)最多为O(n)。
 * 例子：
 * 对于数组A=[2,5,1,1,1,1,4,1,7,3,7]存在下标2,7,9使得数组分成4个分片[2,5],[1,1,1,4],[7],[7]，这3个分片内整数之和相等，所以对于这个数组，函数应该返回true
 * 对于数组A=[10,2,11,13,1,1,1,1,1]，找不到能把数组四等分的下标，所以函数应该返回false
 */
public class Alibaba_OnlineTest {
    //这个实现版本只能适用于数组A全是正整数的情况！若有负数会出现bug.
    static boolean resolve(int[] A) {

        HashMap<Integer, Integer> map = new HashMap();//保存数组的累加和，key为和，value为数组索引,用于后续的常数复杂度的查找操作
        map.put(A[0], 0);
        //在预处理初始化Hashmap的同时,我们也修改了数组A的值,保持的同样是累加和,但是数组A是通过索引来访问的
        for (int i = 1; i < A.length; i++) {
            A[i] += A[i - 1];
            map.put(A[i], i);
        }
        //遍历一边数组,在确定了第一个点之后，就得知了每个等分和的值,然后利用hashmap的常数复杂度的查找操作来判断后面三个等分的切片是否存在
        for (int i = 1; i < A.length - 1; i++) {
            if (searchForIndex(i, A[i - 1], 3, A, map)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param index   ：当前搜索到的下标
     * @param sum     ：四等分的中每一等分的和(不包含下标的数值的和)
     * @param restNum ：剩下需要查找的切分下标的数量
     * @param A       ：数组A[]
     * @param map     ：预处理保存的map，用于实现常数复杂度的查找操作
     * @return  ：标志查找过程是否成功的布尔类型返回值
     */
    static private boolean searchForIndex(int index, int sum, int restNum, int[] A, HashMap<Integer, Integer> map) {
        //如果当前查找的切分下标的数值超过了数组的范围,说明当前查找的结果是无法分成四等分的,只能是2等分或者3等分的情况,查找失败
        if (index >= A.length - 1) {
            return false;
        }
        //最后一个切分下标的判断
        if (restNum == 1) {
            return A[A.length - 1] - A[index] == sum;
        }
        if (map.containsKey(sum + A[index])) {
            index = map.get(sum + A[index]) + 1; //查询下一个下标的值,这里可能会出现数组越界访问的异常！
            return searchForIndex(index, sum, restNum - 1, A, map);//继续搜索下一个可能存在的切分下标
        }
        return false;
    }

    public static void main(String[] args) {
        int[] A = {2, 5, 1, 1, 1, 1, 4, 1, 7, 3, 7};
        int[] B = {10, 2, 11, 13, 1, 1, 1, 1, 1};
        int[] C = {1, 2, 1, 13, 4, 1, 1, 1, 1, 1, 10, 4};
        int[] D = {1, 2, 1, 1, 4, 11, 1, 1, 1, 1, 10, 2, 2};
        int[] E = {1,1,1,4,4,4,4,1,2,1};
        System.out.println(resolve(A));
        System.out.println(resolve(B));
        System.out.println(resolve(C));
        System.out.println(resolve(D));
        System.out.println(resolve(E));
    }
}
