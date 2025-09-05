package org.example.dungeonrescue.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class ABTestService {

    private final Random random = new Random();

    public String assignVariant() {
        return random.nextBoolean() ? "A" : "B"; // 50/50 split
    }

    // Variant A (current algorithm)
    public int calculateA(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] dp = new int[n + 1];
        final int INF = Integer.MAX_VALUE;

        for (int j = 0; j <= n; j++) {
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

    // Variant B (alternative algorithm, same problem but slightly different DP)
    public int calculateB(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];
        final int INF = Integer.MAX_VALUE;

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = INF;
            }
        }
        dp[m][n - 1] = dp[m - 1][n] = 1;

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int need = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = Math.max(1, need);
            }
        }
        return dp[0][0];
    }
}
