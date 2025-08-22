package org.example;

public class DungeonRescue {
    public static int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] dp = new int[n + 1];
        final int INF = Integer.MAX_VALUE;

        for (int j = 0; j < n; j++) {
            dp[j] = INF;
        }
        dp[n - 1] = 1;

        for (int i = m - 1; i >= 0; i--) {
            dp[n] = INF;
            for (int j = n - 1; j >= 0; j--) {
                int needNext = Math.min(dp[j], dp[j + 1]);
                int needHere = needNext - dungeon[i][j];
                dp[j] = Math.max(1, needHere);
            }
        }
        return dp[0];
    }

    // Example usage
    public static void main(String[] args) {
        int[][] dungeon1 = {
                {-2, -3,  3},
                {-5,-10,  1},
                {10, 30, -5}
        };
        System.out.println(calculateMinimumHP(dungeon1)); // 7

        int[][] dungeon2 = {{0}};
        System.out.println(calculateMinimumHP(dungeon2)); // 1
    }
}