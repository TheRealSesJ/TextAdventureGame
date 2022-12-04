package com.sesj.Interfaces;

import java.awt.*;

public interface AIEntity {
    String getNamePool();
    void setName(String name);
    void tick();
    Point getPosition();
    String getName();
    void setPosition(Point position);
}
