package org.example.dungeonrescue.controller;

import org.example.dungeonrescue.model.DungeonResult;
import org.example.dungeonrescue.repository.DungeonResultRepository;
import org.example.dungeonrescue.service.ABTestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class DungeonController {

    private final DungeonResultRepository repository;
    private final ObjectMapper objectMapper;
    private final ABTestService abTestService;

    public DungeonController(DungeonResultRepository repository,
                             ObjectMapper objectMapper,
                             ABTestService abTestService) {
        this.repository = repository;
        this.objectMapper = objectMapper;
        this.abTestService = abTestService;
    }

    public record DungeonRequest(int[][] dungeon) {}
    public record DungeonResponse(int minInitialHealth, String variant) {}

    @PostMapping("/min-health")
    @ResponseStatus(HttpStatus.OK)
    public DungeonResponse calculateMinHealth(@RequestBody DungeonRequest request)
            throws JsonProcessingException {

        String variant = abTestService.assignVariant();
        int result = (variant.equals("A"))
                ? abTestService.calculateA(request.dungeon())
                : abTestService.calculateB(request.dungeon());

        String dungeonJson = objectMapper.writeValueAsString(request.dungeon());

        repository.save(new DungeonResult(dungeonJson, result, variant));

        return new DungeonResponse(result, variant);
    }
}
