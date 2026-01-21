package com.danaleeband.guessit.global;

public enum GameState {
    WAITING,        // 아직 시작 전
    COUNTDOWN,      // 3,2,1 카운트 중
    IN_PROGRESS,    // 힌트 공개 중
    FINISHED        // 게임 종료
}
