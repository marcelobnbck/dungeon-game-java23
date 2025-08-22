package org.example.dungeonrescue.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DungeonController {

    // DTO for request
    public record DungeonRequest(int[][] dungeon) {}

    // DTO for response
    public record DungeonResponse(int minInitialHealth) {}

}

