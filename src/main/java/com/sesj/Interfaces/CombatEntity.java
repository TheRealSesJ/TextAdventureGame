package com.sesj.Interfaces;

import com.sesj.GameObjects.Buff;
import com.sesj.GameObjects.Weapon;

import java.awt.Point;

public interface CombatEntity {
  int getHp();
  int getMaxHp();
  Weapon getWeapon();
  void updateHp(int update);
  int getArmor();
  Point getPosition();
  boolean canTraverse();
  boolean buff(Buff buff);
  String getName();
}