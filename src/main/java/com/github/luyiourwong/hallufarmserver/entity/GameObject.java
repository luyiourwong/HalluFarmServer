package com.github.luyiourwong.hallufarmserver.entity;

import lombok.Data;

@Data
public abstract class GameObject {
    protected Position position;
    protected String name;
    protected boolean available = true;
}
