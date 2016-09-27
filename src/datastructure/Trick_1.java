package datastructure;

/**
 * Created by Lujunqiu on 2016/9/27.
 * Given an array of three colors.
 * The array elements have a special property. Whenever two elements of different colors become adjacent to each other, they merge into an element of the third color.
 * How many minimum number of elements can be there in array after considering all possible transformations.
 * 测试用例：
 *  Input : arr[] = {R, G}
    Output : 1
    G B -> {G B} R -> R

    Input : arr[] = {R, G, B}
    Output : 2
    Explanation :
    R G B -> [R G] B ->  B B
    OR
    R G B -> R {G B} ->  R R
 */
public class Trick_1 {
    public static void main(String[] args) {
        char arr[] = {'R','B','G','R'};
        int n = arr.length;
        System.out.println(findMin(arr, n));
    }

    /**
     *在多次测试输出结果后我们归纳发现了一个规律：
     *Let n be number of elements in array. No matter what the input is, we always end up in three types of outputs:
        n: When no transformation can take place at all
        2: When number of elements of each color are all odd(奇数) or all even(偶数)
        1: When number of elements of each color are mix of odd and even
     */
    public static int findMin(char[] a , int n){
        // Initialize counts of all colors as 0
        int b_count = 0, g_count = 0, r_count = 0;

        // Count number of elements of different colors
        for (int i = 0; i < n; i++)
        {
            if (a[i] == 'B') b_count++;
            if (a[i] == 'G') g_count++;
            if (a[i] == 'R') r_count++;
        }

        // Check if elements are of same color
        if (b_count==n || g_count==n || r_count==n)
            return n;
        // 通过位运算检查int数值的奇偶性，全为奇数返回2
        if ((b_count & 1 & r_count & g_count) == 1) {
            return 2;
        }
        // 通过位运算检查int数值的奇偶性，全为偶数返回2
        if ((~b_count & 1 & ~r_count & ~g_count) == 1) {
            return 2;
        }
        //其他情况则返回1
        return 1;
    }
}
