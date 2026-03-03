package com.danaleeband.guessit.global;

public enum GameState {
    WAITING,        // 아직 시작 전
    COUNTDOWN,      // 3,2,1 카운트 중
    HINT,           // 힌트 보여주는 중
    SCORING,        // 정답 공개 및 점수 계산
    FINISHED        // 게임 종료
}
