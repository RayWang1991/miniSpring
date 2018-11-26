package labTest.playGround;

import java.util.Arrays;

public class MaxPath {
    public static int findMaxNum(int[][] a) {
        int n, m;
        if (a == null || (n = a.length) == 0 || (m = a[0].length) == 0) return 0;
        int[][] dps = new int[2][m];
        for (int i = 0; i < n; i++) {
            int[] dpCurrent = dps[i % 2];
            int[] dpLast = dps[(i + 1) % 2];
            for (int j = 0; j < m; j++) {
                int max = a[i][j];
                if (i == 0) {
                    if (j > 0) {
                        max += dpCurrent[j - 1];
                    }
                } else if (j == 0) {
                    max += dpLast[0];
                } else {
                    max += Math.max(dpLast[j], dpCurrent[j - 1]);
                }
                dpCurrent[j] = max;
            }
        }
        return dps[(n - 1) % 2][m - 1];
    }

    public static void main(String[] args) {
        int[][] input = new int[][]{{1, 5, 7, 10}, {8, 1, 0, 13}, {5, 2, 7, 3}};

        System.out.println(findMaxNum(input));
    }
}
