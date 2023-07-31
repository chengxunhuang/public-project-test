package 算法;

/**
 * @author:wuhao
 * @create: 2023-07-03 16:59
 * @Description:
 */
public class 排序 {
    public static void main(String[] args) {
        // int[] a = {2, 1, 9, 3, 5, 0};
        int[] a = {3, 6, 9, 2, 4, 8};
        // 冒泡排序
        // bubbleSolt(a, 6);
        // 插入排序
        insertSort(a, 6);
        // 选择排序
        // selectSort(a, 6);
        // mergeSort(a, 0, a.length - 1);
        // quickSort(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    /**
     * 冒泡排序,升序   2   1  9  3   5  0
     */
    public static void bubbleSolt(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 插入排序 ，升序 , 2 1  9  3   5  0  。
     */
    public static void insertSort(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 1; i < n; i++) {
            int value = a[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    break;  // 找到了插入的位置
                }
            } // 注意下面的j+1的j来自于 上面的for循环
            a[j + 1] = value;
        }
    }

    /**
     * 选择排序 ，升序 , 2  1  9  3   5  0  。
     */
    public static void selectSort(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 0; i < n; i++) {
            int min = a[i];
            int k = i; // k一定要等于 i ，如果从   k = 0 开始的话  ，如果目前这个位置就是最小值的话  ，那会与 0 位置发生调换 ，所以应该是 k = i
            for (int j = i + 1; j < n; j++) {
                if (min > a[j]) {
                    min = a[j];
                    k = j;
                }
            }
            a[k] = a[i];
            a[i] = min;
        }
    }


    /**
     * 归并排序，将两个排好序的合并到一起   2  1  9    3   5  0
     */
    public static void mergeSort(int[] value, int m, int n) {
        // 数组中只有一个元素 ，不用再继续分解
        if (m >= n) {
            return;
        }
        int mid = (m + n) / 2;
        mergeSort(value, m, mid);
        mergeSort(value, mid + 1, n);
        // 将两个子排序合并到一起 用临时数组
        merge(value, m, mid, n);
    }

    public static void merge(int[] nums, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start; // 左部分首元素
        int j = mid + 1; // 右部分首元素
        int k = 0;
        while (i <= mid && j <= end) { // 左指针 与 右指针 不能越界
            if (nums[i] < nums[j]) {
                temp[k++] = nums[i];
                i++;
            } else {
                temp[k++] = nums[j];
                j++;
            }
        }
        // 其中一个遍历完之后，直接将另一部分没有遍历完的 添加到 新数组后面即可
        while (i <= mid) { // 右边遍历完事了   左边还剩呢
            temp[k++] = nums[i++];
        }
        while (j <= end) { // 左边遍历完事了   右边还剩了
            temp[k++] = nums[j++];
        }
        k = 0;
        i = start; // 将temp中的元素  全部都copy到原数组里边去
        for (; i <= end; i++, k++) {
            nums[i] = temp[k];
        }
    }


    /**
     * 快速排序    2  1  9    3   5  0
     */
    public static void quickSort(int[] nums, int start, int end) { // 左闭右闭
        if (start >= end) { // 当数组中只有一个元素的时候终止
            return;
        }
        int pIndex = partition(nums, start, end);
        quickSort(nums, start, pIndex - 1);
        quickSort(nums, pIndex + 1, end);
    }

    private static int partition(int[] a, int p, int r) {
        // 选择最后一个数字作为支点
        int pivot = a[r];
        int i = p;
        for (int j = p; j < r; j++) {
            // 当出现 a[j] > pivot 的时候 ， i  并没有 ++ ， 所以会有 else
            if (a[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    int tmp = a[i];
                    a[i++] = a[j];
                    a[j] = tmp;
                }
            }
        }
        // 此时 将支点 换到 中间 ，左边小于它 ，右边大于 它
        int tmp = a[i];
        a[i] = a[r];
        a[r] = tmp;
        return i;
    }


}
