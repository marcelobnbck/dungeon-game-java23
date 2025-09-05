package org.example.dungeonrescue.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "dungeon_results")
public class DungeonResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String dungeonJson;

    @Column(nullable = false)
    private int minInitialHealth;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false, length = 1)
    private String variant; // "A" or "B"

    public DungeonResult() {}

    public DungeonResult(String dungeonJson, int minInitialHealth, String variant) {
        this.dungeonJson = dungeonJson;
        this.minInitialHealth = minInitialHealth;
        this.variant = variant;
    }

    public Long getId() { return id; }
    public String getDungeonJson() { return dungeonJson; }
    public void setDungeonJson(String dungeonJson) { this.dungeonJson = dungeonJson; }
    public int getMinInitialHealth() { return minInitialHealth; }
    public void setMinInitialHealth(int minInitialHealth) { this.minInitialHealth = minInitialHealth; }
    public Instant getCreatedAt() { return createdAt; }
    public String getVariant() { return variant; }
    public void setVariant(String variant) { this.variant = variant; }
}
