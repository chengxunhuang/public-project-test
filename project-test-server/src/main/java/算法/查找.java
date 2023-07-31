package 算法;

/**
 * @Author: wh
 * @Date: 2023/07/31/9:22
 * @Description:
 */
public class 查找 {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7, 9, 10};
        int[] a1 = {1, 3, 5, 5, 5, 9, 10};
        int[] a2 = {1};
        System.out.println("查找元素5的位置：" + bsearch1(a1, 7, 5));
        System.out.println("查找元素5的位置：" + bsearch2(a1, 1, 2));
    }

    /**
     * 二分查找的递归实现 - 1.不存在重复元素的有序数组中
     */
    public static int bsearch1(int[] a, int n, int val) {
        return bsearchInternally(a, 0, n - 1, val);
    }

    private static int bsearchInternally(int[] a, int low, int high, int value) {
        if (low > high) return -1;
        // mid=(low+high)/2 这种写法是有问题的。因为如果 low 和 high 比较大的话，两者之和就有可能会溢出。
        // low+(high-low)/2。更进一步，如果要将性能优化到极致的话，我们可以将这里的除以 2 操作转化成位运算 low+((high-low)>>1)
        int mid = low + ((high - low) >> 1);
        if (a[mid] == value) {
            return mid;
        } else if (a[mid] < value) {
            return bsearchInternally(a, mid + 1, high, value);
        } else {
            return bsearchInternally(a, low, mid - 1, value);
        }
    }

    /**
     * 二分查找的递归实现 - 2.数组中存在重复的元素
     *
     * @param a
     * @param n
     * @param value
     * @return
     */
    public static int bsearch2(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                // 获取第一个重复元素的位置，其中最关键的地方在于，如果找到相同的元素，如何判断就是第一个呢？
                if ((mid == 0) || (a[mid - 1] != value)) return mid;
                else high = mid - 1;

                // 获取最后一个重复元素的位置，其中最关键的地方在于，如果找到相同的元素，如何判断就是最后一个呢？
                // if ((mid == n - 1) || (a[mid + 1] != value)) return mid;
                // else low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     *
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int bsearch3(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] >= value) {
                if ((mid == 0) || (a[mid - 1] < value)) return mid;
                else high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }


    /**
     * 查找最后一个小于等于给定值的元素
     *
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int bsearch4(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else {
                if ((mid == n - 1) || (a[mid + 1] > value)) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }

}
