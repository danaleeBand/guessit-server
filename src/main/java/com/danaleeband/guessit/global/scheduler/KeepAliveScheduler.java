package com.danaleeband.guessit.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeepAliveScheduler {

    private final RestTemplate restTemplate;

    @Scheduled(cron = "0 */10 * * * *")
    public void pingSelf() {
        try {
            String response = restTemplate.getForObject(
                "https://guessit-server.onrender.com/health",
                String.class
            );

            log.info("[KeepAlive] self ping response={}", response);
        } catch (Exception e) {
            log.error("[KeepAlive] self ping failed", e);
        }
    }
}
