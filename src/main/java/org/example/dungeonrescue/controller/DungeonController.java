package org.example.dungeonrescue.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DungeonController {

    // DTO for request
    public record DungeonRequest(int[][] dungeon) {}

    // DTO for response
    public record DungeonResponse(int minInitialHealth) {}

    @PostMapping("/min-health")
    @ResponseStatus(HttpStatus.OK)
    public DungeonResponse calculateMinHealth(@RequestBody DungeonRequest request) {
        int result = calculateMinimumHP(request.dungeon());
        return new DungeonResponse(result);
    }

    private int calculateMinimumHP(int[][] dungeon) {
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
}

