package com.sesj.Interfaces;

import com.sesj.GameObjects.Weapon;

public interface Entity{
  int getHp();
  int getMaxHp();
  Weapon getWeapon();
  void updateHp(int update);
  int getArmor();
  String getStats();
  int getXPos();
  int getYPos();
  boolean canTraverse();
}