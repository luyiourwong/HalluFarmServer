package com.github.luyiourwong.hallufarmserver.entity;

import lombok.Data;

@Data
public class Character {
    private String name;
    private Position position;
    private CharacterState state = CharacterState.IDLE;

    public enum CharacterState {
        IDLE, MOVING, EATING, SLEEPING
    }
}
