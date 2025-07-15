package com.github.luyiourwong.hallufarmserver.service;

import com.github.luyiourwong.hallufarmserver.entity.Character;
import com.github.luyiourwong.hallufarmserver.entity.ConsumableItem;
import com.github.luyiourwong.hallufarmserver.entity.Position;
import com.github.luyiourwong.hallufarmserver.entity.ReusableItem;
import com.github.luyiourwong.hallufarmserver.map.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class GameService {
    private final Room room;
    private final ConsumableItem bread;
    private final ReusableItem bed;
    private final Character characterA;
    private final Character characterB;

    public GameService() {
        room = new Room();

        // 初始化面包
        bread = new ConsumableItem();
        bread.setName("Bread");
        bread.setPosition(new Position(0, 0));
        bread.setRemainingUses(3);

        // 初始化床
        bed = new ReusableItem();
        bed.setName("Bed");
        bed.setPosition(new Position(2, 2));

        // 初始化角色
        characterA = new Character();
        characterA.setName("A");
        characterA.setPosition(new Position(0, 1));

        characterB = new Character();
        characterB.setName("B");
        characterB.setPosition(new Position(2, 1));
    }

    // 角色移动逻辑
    public boolean moveCharacter(Character character, Position newPos) {
        if (!room.isValidPosition(newPos)) {
            return false;
        }

        // 使用乐观锁尝试移动
        synchronized (room) {
            if (room.getCharacterAt(newPos).isPresent()) {
                return false;
            }
            Position oldPos = character.getPosition();
            character.setPosition(newPos);
            room.updateCharacterPosition(character, oldPos, newPos);
            return true;
        }
    }

    // 吃面包逻辑
    public boolean eatBread(Character character) {
        if (!character.getPosition().equals(bread.getPosition())) {
            return false;
        }

        synchronized (bread) {
            if (!bread.isAvailable()) {
                return false;
            }
            character.setState(Character.CharacterState.EATING);
            return bread.consume();
        }
    }

    // 使用床逻辑
    public boolean useBed(Character character) {
        if (!character.getPosition().equals(bed.getPosition())) {
            return false;
        }

        if (bed.startUsing(character)) {
            character.setState(Character.CharacterState.SLEEPING);
            return true;
        }
        return false;
    }

    // 停止使用床
    public void stopUsingBed(Character character) {
        if (bed.getCurrentUser() == character) {
            character.setState(Character.CharacterState.IDLE);
            bed.stopUsing();
        }
    }
}
