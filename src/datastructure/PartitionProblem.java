package datastructure;

/**
 * Created by Lujunqiu on 2016/10/11.
 * Partition problem is to determine whether a given set(positive number only) can be partitioned into two subsets such that the sum of elements in both subsets is same.

 Examples:
 arr[] = {1, 5, 11, 5}
 Output: true
 The array can be partitioned as {1, 5, 5} and {11}

 arr[] = {1, 5, 3}
 Output: false
 The array cannot be partitioned into equal sum sets.

 思路：首先考虑recursive solution，自上而下递归分解问题的规模，一步步求解：
 Let isSubsetSum(arr, n, sum/2) be the function that returns true if there is a subset of arr[0..n-1] with sum equal to sum/2；
 isSubsetSum (arr, n, sum/2) = isSubsetSum (arr, n-1, sum/2) || isSubsetSum (arr, n-1, sum/2 - arr[n-1])；
 也即：以最后一个元素为考虑的出发点，我们需要寻找的subset要不就不包含最后一个元素直接与isSubsetSum (arr, n-1, sum/2)的解相同（此时规模减1）；
       要不就包含最后一个元素与问题isSubsetSum (arr, n-1, sum/2 - arr[n-1])同解。

 然后我们根据recursive solution的思路，利用dynamic programming （自下而上迭代）来解决问题，保留迭代过程的结果，为后续迭代所用。

 以上算法需要我们的初始数组全是正数，如果有负数存在，我们需要进行预处理：把数组中的每一个元素都加上数组中最小负数的绝对值，这样处理之后的数组就是全是正数了。
 */
public class PartitionProblem {
    static boolean findPartition (int arr[], int n)
    {
        int sum = 0;
        int i, j;

        // Calculate sun of all elements
        for (i = 0; i < n; i++)
        {
            sum += arr[i];
        }
        // if sum is odd,there can not be two subsets with equal sum, so return false
        if (sum%2 != 0)
        {
            return false;
        }
        //part[i][j] = true if a subset of {arr[0], arr[1], ..arr[j-1]} has sum equal to i, otherwise false
        boolean part[][]=new boolean[sum/2+1][n+1];

        // initialize top row as true
        for (i = 0; i <= n; i++)
            part[0][i] = true;

        // initialize leftmost column, except part[0][0], as false
        for (i = 1; i <= sum/2; i++)
            part[i][0] = false;

        // Fill the partition table in bottom up manner(自下而上迭代)
        for (i = 1; i <= sum/2; i++)
        {
            for (j = 1; j <= n; j++)
            {
                part[i][j] = part[i][j-1];
                if (i >= arr[j-1])//要求我们的数组里面全是非负数，如何有负数存在需要对初始数组进行预处理
                    part[i][j] = part[i][j] ||
                            part[i - arr[j-1]][j-1];
            }
        }
        return part[sum/2][n];
    }

    /*Driver function to check for above function*/
    public static void main (String[] args)
    {
        int arr1[] = {1, 5, 5,11};
        int n = arr1.length;
        if (findPartition(arr1, n) == true)
            System.out.println("true");
        else
            System.out.println("false");

    }
}
