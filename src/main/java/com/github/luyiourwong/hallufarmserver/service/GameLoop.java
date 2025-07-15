package com.github.luyiourwong.hallufarmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class GameLoop {

    private GameService gameService;
    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    private final Sinks.Many<GameState> stateSink = Sinks.many().multicast().onBackpressureBuffer();

    @Scheduled(fixedRate = 100)
    public void update() {
        // 更新游戏状态
        GameState currentState = new GameState(/* 当前状态信息 */);
        stateSink.tryEmitNext(currentState);
    }
}
