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
  String getArt();

  default String getHealthbar(int hp, int maxHp) { //TODO make this an abstract class
    double ratio = (double) hp/maxHp;
    int filled = (int) (10*ratio);
    int unfilled = 10-filled;
    return "[x]".repeat(filled)+"[ ]".repeat(unfilled);
  }
}