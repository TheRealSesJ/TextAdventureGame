package com.sesj.Interfaces;

import com.sesj.GameObjects.Buff;
import com.sesj.GameObjects.Weapon;
import com.sesj.UtilityObjects.Coordinate;


public interface CombatEntity {
  int getHp();
  int getMaxHp();
  Weapon getWeapon();
  void updateHp(int update);
  int getArmor();
  Coordinate getPosition();
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