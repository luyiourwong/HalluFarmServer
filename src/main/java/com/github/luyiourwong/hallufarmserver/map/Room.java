package com.github.luyiourwong.hallufarmserver.map;

import com.github.luyiourwong.hallufarmserver.entity.GameObject;
import com.github.luyiourwong.hallufarmserver.entity.Position;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Room {
    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;

    private final Map<Position, GameObject> itemsMap = new ConcurrentHashMap<>();
    private final Map<Position, Character> charactersMap = new ConcurrentHashMap<>();

    // 检查位置是否在房间内
    public boolean isValidPosition(Position pos) {
        return pos.getX() >= 0 && pos.getX() < WIDTH &&
                pos.getY() >= 0 && pos.getY() < HEIGHT;
    }

    // 获取指定位置的物品
    public Optional<GameObject> getItemAt(Position pos) {
        return Optional.ofNullable(itemsMap.get(pos));
    }

    // 获取指定位置的角色
    public Optional<Character> getCharacterAt(Position pos) {
        return Optional.ofNullable(charactersMap.get(pos));
    }
}

