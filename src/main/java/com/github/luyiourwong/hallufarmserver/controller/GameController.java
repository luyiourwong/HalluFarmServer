package com.github.luyiourwong.hallufarmserver.controller;

import com.github.luyiourwong.hallufarmserver.entity.Position;
import com.github.luyiourwong.hallufarmserver.service.GameLoop;
import com.github.luyiourwong.hallufarmserver.service.GameService;
import com.github.luyiourwong.hallufarmserver.service.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class GameController {
    private GameService gameService;
    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    private GameLoop gameLoop;
    @Autowired
    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    @GetMapping(path = "/state", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GameState> streamState() {
        return gameLoop.getStateFlux();
    }

    @PostMapping("/move/{characterId}")
    public Mono<Boolean> moveCharacter(@PathVariable String characterId,
                                       @RequestBody Position newPosition) {
        return Mono.fromCallable(() ->
                                         gameService.moveCharacter(
                                                 characterId.equals("A") ? gameService.getCharacterA() : gameService.getCharacterB(),
                                                 newPosition
                                         )
        );
    }
}

