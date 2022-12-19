package com.sesj.Interfaces;

import com.sesj.GameObjects.Player;

import java.awt.*;

public interface AIEntity {
    String getNamePool();
    void setName(String name);
    void tick(Player player);
    Point getPosition();
    String getName();
    void setPosition(Point position);
}
