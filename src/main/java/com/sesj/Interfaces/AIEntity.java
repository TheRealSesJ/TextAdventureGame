package com.sesj.Interfaces;

import com.sesj.GameObjects.Player;
import com.sesj.UtilityObjects.Coordinate;


public interface AIEntity {
    String getNamePool();
    void setName(String name);
    void tick(Player player);
    Coordinate getPosition();
    String getName();
    void setPosition(Coordinate position);
}
