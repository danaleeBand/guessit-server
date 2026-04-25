# guessit-server

힌트를 보고 정답을 맞히는 실시간 멀티플레이어 단어 맞추기 게임 서버입니다.  
Spring Boot + WebSocket(STOMP) 기반으로 최대 5명이 실시간으로 대결할 수 있습니다.

## 기술 스택

| 분류              | 기술                          |
|-----------------|-----------------------------|
| Language        | Java 17                     |
| Framework       | Spring Boot 3.4.0           |
| Database        | PostgreSQL (퀴즈 데이터)         |
| Cache / Session | Redis (방, 플레이어 상태)          |
| 실시간 통신          | WebSocket (STOMP)           |
| API 문서          | Springdoc OpenAPI (Swagger) |
| 빌드              | Gradle                      |
| 배포              | Docker, Render              |

## API 문서

| 환경 | URL                                          |
|----|----------------------------------------------|
| 로컬 | http://localhost:8080/api-docs               |
| 배포 | https://guessit-server.onrender.com/api-docs |

## 게임 플로우

```
방 생성 / 입장
    └─> 플레이어 Ready
            └─> 게임 시작 (COUNTDOWN → 3, 2, 1)
                    └─> 힌트 공개 (HINT) — 5초 간격, 최대 6개
                            └─> 정답 제출 (submit)
                                    └─> 점수 계산 (SCORING)
                                            ├─> 다음 라운드 (있으면 반복)
                                            └─> 게임 종료 (FINISHED)
```

### 점수 규칙

| 정답 순위 | 점수 |
|-------|----|
| 1등    | 5점 |
| 2등    | 3점 |
| 3등 이하 | 1점 |
| 오답    | 0점 |

모든 플레이어가 제출하면 남은 힌트를 즉시 공개하고 채점으로 넘어갑니다.

## 로컬 실행

### 사전 요구사항

- Java 17+
- PostgreSQL
- Redis

### 빌드 및 실행

```bash
./gradlew clean build -x test
./gradlew bootRun
```

### Docker로 실행

```bash
docker build -t guessit-server .
docker run -p 8080:8080 --env-file src/main/resources/.env guessit-server
```

## 프로젝트 구조

```
src/main/java/com/danaleeband/guessit/
├── config/          # WebSocket, Redis, Swagger, CORS 설정
├── global/          # 상수, GameState enum
├── room/            # 방 생성·입장·퇴장, WebSocket 세션 관리
├── player/          # 플레이어 생성, Ready 상태 관리
├── quiz/            # 퀴즈 조회, 힌트 관리
└── game/            # 게임 진행, 정답 채점, 점수 계산
```
