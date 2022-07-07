package com.sesj.Interfaces;

import com.sesj.GameObjects.Weapons.*;

public interface Entity{
  int getHp();
  Weapon getWeapon();
  void updateHp(int update);
  int getArmor();
  String getStats();
}