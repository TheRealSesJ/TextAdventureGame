package Interfaces;

import GameObjects.Weapons.*;

public interface Entity{
  public int getHp();
  public Weapon getWeapon();
  public void updateHp(int update);
  public int getArmor();
  public String getStats();
}