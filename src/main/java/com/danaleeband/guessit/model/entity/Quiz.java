package com.danaleeband.guessit.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity
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

    public Quiz() {
    }

    public Quiz(String answer, String hint1, String hint2, String hint3, String hint4, String hint5, String hint6) {
        this.answer = answer;
        this.hint1 = hint1;
        this.hint2 = hint2;
        this.hint3 = hint3;
        this.hint4 = hint4;
        this.hint5 = hint5;
        this.hint6 = hint6;
    }

    // getter, setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHint1() {
        return hint1;
    }

    public void setHint1(String hint1) {
        this.hint1 = hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public void setHint2(String hint2) {
        this.hint2 = hint2;
    }

    public String getHint3() {
        return hint3;
    }

    public void setHint3(String hint3) {
        this.hint3 = hint3;
    }

    public String getHint4() {
        return hint4;
    }

    public void setHint4(String hint4) {
        this.hint4 = hint4;
    }

    public String getHint5() {
        return hint5;
    }

    public void setHint5(String hint5) {
        this.hint5 = hint5;
    }

    public String getHint6() {
        return hint6;
    }

    public void setHint6(String hint6) {
        this.hint6 = hint6;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

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
