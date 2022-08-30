package com.sesj.Interfaces;

import com.sesj.GameObjects.Buff;
import com.sesj.GameObjects.Weapon;

import java.awt.*;

public interface Entity {
  int getHp();
  int getMaxHp();
  Weapon getWeapon();
  void updateHp(int update);
  int getArmor();
  String getStats();
  Point getPosition();
  boolean canTraverse();
  void tick();
  boolean buff(Buff buff);
}