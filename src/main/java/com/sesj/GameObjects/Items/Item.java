//planning on interface that includes the item

package com.sesj.GameObjects.Items;

public class Item{
  //protected changeable attributes
  private String name;
  
  //weapon related
  private int attackBoost;
  private int speedBoost;
  private int accuracyBoost;
  private boolean rangeBoost;

  //player related
  private int moveBoost;
  private boolean traverseBoost;
  private int hpBoost;
  private int armorBoost;  

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
    return toString()
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