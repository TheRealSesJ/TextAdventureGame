//planning on interface that includes the item

package com.sesj.GameObjects;

import com.sesj.Interfaces.GameObject;

public class Item implements GameObject {
  //weapon related
  private final int attackBoost;
  private final int speedBoost;
  private final int accuracyBoost;
  private final boolean rangeBoost;

  //player related
  private final int moveBoost;
  private final boolean traverseBoost;
  private final boolean scanBoost;
  private final int hpBoost;
  private final int armorBoost;

  private final String name;

  public Item(int attack, int speed, int accuracy, boolean range, int movement, boolean traversal, boolean scan, int hp, int armor, String name){
    this.attackBoost=attack;
    this.speedBoost=speed;
    this.accuracyBoost=accuracy;
    this.rangeBoost=range;
    this.moveBoost=movement;
    this.traverseBoost=traversal;
    this.scanBoost = scan;
    this.hpBoost=hp;
    this.armorBoost=armor;
    this.name=name;
  }

  
  public String toString(){ return name; }

  //weapon related getters
  public int getAttackBoost(){
    return this.attackBoost;
  }

  public int getSpeedBoost(){
    return this.speedBoost;
  }

  public int getAccuracyBoost(){
    return this.accuracyBoost;
  }

  public boolean getRangeBoost(){
    return this.rangeBoost;
  }

  //player related getters
  public int getMoveBoost(){
    return this.moveBoost;
  }

  public boolean getTraverseBoost(){
    return this.traverseBoost;
  }

  public boolean getScanBoost(){
    return this.scanBoost;
  }

  public int getHpBoost(){
    return this.hpBoost;
  }

  public int getArmorBoost(){
    return this.armorBoost;
  }

  public String getStats(){
    return "\nItem: "+this+"\n"
    +"\nWeapon Related:"
    +"\n\tattack boost: "+ this.attackBoost
    +"\n\tspeed: "+ this.speedBoost
    +"\n\taccuracy: "+ this.accuracyBoost
    +"\n\tranged: "+ this.rangeBoost
    +"\n"
    +"\nPlayer Related: "
    +"\n\tmove boost: "+ this.moveBoost
    +"\n\ttraversal boost: "+ this.traverseBoost
    +"\n\tscan boost: "+ this.scanBoost
    +"\n\thp boost:" + this.hpBoost
    +"\n\tarmor boost: " + this.armorBoost
    +"\n";
  }
}