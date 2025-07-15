package com.github.luyiourwong.hallufarmserver.entity;

import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class ReusableItem extends GameObject {
    private final Lock usageLock = new ReentrantLock();
    private Character currentUser = null;

    public boolean startUsing(Character character) {
        if (usageLock.tryLock()) {
            currentUser = character;
            return true;
        }
        return false;
    }

    public void stopUsing() {
        currentUser = null;
        usageLock.unlock();
    }
}

