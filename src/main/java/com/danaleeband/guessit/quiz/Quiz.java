package com.danaleeband.guessit.quiz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String answer;

    @Column(name = "hint_1", nullable = false, length = 25)
    private String hint1;

    @Column(name = "hint_2", nullable = false, length = 25)
    private String hint2;

    @Column(name = "hint_3", nullable = false, length = 25)
    private String hint3;

    @Column(name = "hint_4", nullable = false, length = 25)
    private String hint4;

    @Column(name = "hint_5", nullable = false, length = 25)
    private String hint5;

    @Column(name = "hint_6", nullable = false, length = 25)
    private String hint6;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
