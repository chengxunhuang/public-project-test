package 算法.思想;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @Author: wh
 * @Date: 2023/08/25/15:02
 * @Description:
 */
public class 回溯 {

    @Test
    public void test1() {
        System.out.println(solveNQueens(4));
    }

    /**
     * 八皇后问题
     * https://leetcode.cn/problems/eight-queens-lcci/solutions/403802/ba-huang-hou-by-leetcode-solution/
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> solutions = new ArrayList<List<String>>();
        int[] queens = new int[n];
        // 替换 queens中的所有值为-1
        Arrays.fill(queens, -1);
        Set<Integer> columns = new HashSet<Integer>();
        Set<Integer> diagonals1 = new HashSet<Integer>();
        Set<Integer> diagonals2 = new HashSet<Integer>();
        backtrack(solutions, queens, n, 0, columns, diagonals1, diagonals2);
        return solutions;
    }

    /**
     * 每次放置皇后时，对于每个位置判断其是否在三个集合中，如果三个集合都不包含当前位置，则当前位置是可以放置皇后的位置。
     *
     * @param solutions
     * @param queens
     * @param n          多少皇后
     * @param row        从0行还是放
     * @param columns    列
     * @param diagonals1
     * @param diagonals2
     */
    public void backtrack(List<List<String>> solutions, int[] queens, int n, int row, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {
        if (row == n) {
            List<String> board = generateBoard(queens, n);
            solutions.add(board);
        } else {
            for (int i = 0; i < n; i++) {
                if (columns.contains(i)) {
                    continue;
                }
                // 斜线行下标 - 列下标 如果一致代表再同一个右斜线上
                int diagonal1 = row - i;
                if (diagonals1.contains(diagonal1)) {
                    continue;
                }
                // 斜线行下标 + 列下表 如果一致代表再同一个左斜线上
                int diagonal2 = row + i;
                if (diagonals2.contains(diagonal2)) {
                    continue;
                }
                queens[row] = i;
                columns.add(i);
                diagonals1.add(diagonal1);
                diagonals2.add(diagonal2);
                backtrack(solutions, queens, n, row + 1, columns, diagonals1, diagonals2);
                queens[row] = -1;
                columns.remove(i);
                diagonals1.remove(diagonal1);
                diagonals2.remove(diagonal2);
            }
        }
    }

    /**
     * [..Q., Q..., ...Q, .Q..] Q代表皇后位置
     * @param queens
     * @param n
     * @return
     */
    public List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }
}
