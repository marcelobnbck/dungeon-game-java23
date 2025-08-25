package org.example.dungeonrescue.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "dungeon_results")
public class DungeonResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store the grid as JSON text
    @Lob
    @Column(nullable = false)
    private String dungeonJson;

    @Column(nullable = false)
    private int minInitialHealth;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // Constructors
    public DungeonResult() {}

    public DungeonResult(String dungeonJson, int minInitialHealth) {
        this.dungeonJson = dungeonJson;
        this.minInitialHealth = minInitialHealth;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getDungeonJson() {
        return dungeonJson;
    }

    public void setDungeonJson(String dungeonJson) {
        this.dungeonJson = dungeonJson;
    }

    public int getMinInitialHealth() {
        return minInitialHealth;
    }

    public void setMinInitialHealth(int minInitialHealth) {
        this.minInitialHealth = minInitialHealth;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}
