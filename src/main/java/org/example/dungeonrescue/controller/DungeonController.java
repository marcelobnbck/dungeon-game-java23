package org.example.dungeonrescue.controller;

import org.example.dungeonrescue.model.DungeonResult;
import org.example.dungeonrescue.repository.DungeonResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class DungeonController {

    private final DungeonResultRepository repository;
    private final ObjectMapper objectMapper;

    public DungeonController(DungeonResultRepository repository,
                             ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public record DungeonRequest(int[][] dungeon) {}
    public record DungeonResponse(int minInitialHealth) {}

    @PostMapping("/min-health")
    @ResponseStatus(HttpStatus.OK)
    public DungeonResponse calculateMinHealth(@RequestBody DungeonRequest request)
            throws JsonProcessingException {

        int result = calculateMinimumHP(request.dungeon());

        // Serialize the dungeon grid to JSON
        String dungeonJson = objectMapper.writeValueAsString(request.dungeon());

        // Persist to database
        DungeonResult record = new DungeonResult(dungeonJson, result);
        repository.save(record);

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
