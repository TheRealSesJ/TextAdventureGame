package com.sesj.Interfaces;

import com.sesj.GameObjects.Weapons.*;

public interface Entity{
  public int getHp();
  public Weapon getWeapon();
  public void updateHp(int update);
  public int getArmor();
  public String getStats();
}