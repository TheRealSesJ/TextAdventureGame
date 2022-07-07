//planning on interface that includes the item

package com.sesj.GameObjects;

public class Item{
  //weapon related
  private final int attackBoost;
  private final int speedBoost;
  private final int accuracyBoost;
  private final boolean rangeBoost;

  //player related
  private final int moveBoost;
  private final boolean traverseBoost;
  private final int hpBoost;
  private final int armorBoost;

  private final String name;

  public Item(int attack, int speed, int accuracy, boolean range, int movement, boolean traversal, int hp, int armor, String name){
    this.attackBoost=attack;
    this.speedBoost=speed;
    this.accuracyBoost=accuracy;
    this.rangeBoost=range;
    this.moveBoost=movement;
    this.traverseBoost=traversal;
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

  public int getHpBoost(){
    return this.hpBoost;
  }

  public int getArmorBoost(){
    return this.armorBoost;
  }

  public String getStats(){
    return this
    +"\nWeapon Related:"
    +"\nattack boost: "+ this.attackBoost
    +"\nspeed: "+ this.speedBoost
    +"\naccuracy: "+ this.accuracyBoost
    +"\nranged: "+ this.rangeBoost
    +"\n"
    +"\nPlayer Related: "
    +"\nmove boost: "+ this.moveBoost
    +"\ntraversal boost: "+ this.traverseBoost
    +"\nhp boost:" + this.hpBoost
    +"\narmor boost: " + this.armorBoost
    +"\n";
  }
}