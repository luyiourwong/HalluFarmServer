package com.github.luyiourwong.hallufarmserver.entity;

import lombok.Data;

@Data
public class ConsumableItem extends GameObject {
    private int remainingUses;

    public boolean consume() {
        if (remainingUses > 0) {
            remainingUses--;
            if (remainingUses == 0) {
                available = false;
            }
            return true;
        }
        return false;
    }
}
